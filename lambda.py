import json
import pymysql
import logging
import rds_config
import base64
import requests

# Decode base 64 and return to ascii strings
def decode(message):
    b64_bytes = message.encode('ascii')
    message_bytes = base64.b64decode(b64_bytes)
    message = message_bytes.decode('ascii')
    print(message)
    return message
    
def encode(message):
    message_bytes = message.encode('ascii')
    base64_bytes = base64.b64encode(message_bytes)
    base64_message = base64_bytes.decode('ascii')
    return(base64_message)

def lambda_handler(event, context):
    
    # rds settings
    rds_host  = rds_config.db_host
    name = rds_config.db_username
    password = rds_config.db_password
    db_name = rds_config.db_name
    
    
    logger = logging.getLogger()
    logger.setLevel(logging.INFO)
    
    # Connect to the RDS database
    try:
        conn = pymysql.connect(host=rds_host, user=name, password=password, database=db_name)
    except pymysql.MySQLError as e:
        logger.error("ERROR: Unexpected error: Could not connect to MySQL instance.")
        logger.error(e)
        print(e)
        sys.exit()
    
    # Get the operation to be performed
    try:
        op = None
        if event['httpMethod'] == 'POST':
            op = json.loads(event['body'])['operation']
        elif event['httpMethod'] == 'GET':
            op = event['queryStringParameters']['operation']
    except KeyError:
        return("Missing operation param")
    
    # Show all tables in DB, (unneeded)
    # with conn.cursor() as cur:
    #     cur.execute("show tables")
    #     tables = []
    #     for table in cur.fetchall():
    #         tables.append(table[0])
            
    # Sean
    def addAccount():
        with conn.cursor() as cur:
            body = json.loads(event['body'])
            print('here 1')
            try:
                email = body['email']
                password = body['password']
                role = body['role']
                phone = body["phone"]
                name = body["name"]
                address = body["address"]
                print('here 2')
            except KeyError:
                print('keyError!!')
                return("Request 7 arguments: operation, email, password, role, phone, name, address")
            cur.execute('select * from users where email = %s', email)
            if cur.rowcount != 0:
                print('user already exists...')
                string = ("User %s already exists" % email)
                return({"status": "failure", "message": string})
            
            print("after check")
            query = 'insert into users (email, password, name, phone, address, role) values (\'{}\', \'{}\', \'{}\', \'{}\', \'{}\', \'{}\');'.format(email, password, name, phone, address, role)
            print('about to execute query: ' + query)
            cur.execute(query)
            conn.commit()
            print('executed and committed')
            print('about to return')
            return({"status": "success", "message": email + " added"})
        
    
    # Sean
    def validate():
        try:
            token = event['queryStringParameters']['token']
        except KeyError:
            return({"status": "failure", "message": "Token parameter missing"})
        try:
            print(token)
            userpass = decode(token).split(":")
            print(userpass)
            email = userpass[0]
            password = userpass[1]
        except:
            return({"status": "failure", "message": "Error decoding token"})
            
        with conn.cursor() as cur:
            cur.execute('select * from users where email = "{}" AND password = "{}"'.format(email, password))
            if cur.rowcount == 1:
                account = cur.fetchone()
                return({"status": "success", "id": str(account[0]), "role": str(account[6])})
            else:                    
                return({"status": "failure", "message": "User not found"})
        #return({"status" : "incomplete"})
            
    # Sean    
    def getSicknesses():
        params = json.loads(event['body'])
        query = {}
        query['name'] = params.get('name') or '%'
        query['symptoms'] = '|'.join(params.get('symptoms')) or '%' #makes regex or separated list of all symptoms
        query['species'] = params.get('species') or '%'
        
        with conn.cursor() as cur:
            cur.execute("""select * from sicknesses
                         where name LIKE %s
                         and symptoms REGEXP %s
                         and species_affected like %s """, 
                         ('%' + query['name'] + '%', query['symptoms'], '%' + query['species'] + '%'))
            response = []          
            for row in cur.fetchall():
                disease = {}
                disease['name'] = row[1]
                disease['symptoms'] = row[2]
                disease['species_affected'] = row[3]
                disease["description"] = row[4]
                disease['resource_url'] = row[5]
                response.append(disease)
        return({"status" : "success", "response": response})
       
             
    # Sean   
    def getPrescriptions():
        params = event['queryStringParameters']
        query = {}
        query['petid'] = params.get('petid')
        query['ownerid'] = params.get('ownerid')
        with conn.cursor() as cur:
            cur.execute("""SELECT * FROM prescriptions WHERE ownerid = %s AND petid = %s """, (query['ownerid'], query['petid']))
            response = []
            for row in cur.fetchall():
                preInfo = {}
                preInfo['prescription_name'] = row[2]
                preInfo['dosage'] = row[3]
                preInfo['dt_start'] = str(row[4])
                preInfo['dt_end'] = str(row[5])
                response.append(preInfo)
        
        if not response:
            return ({"status" : "failure"})
        else:
            return({"status" : "success", "response" : response})
             
    # Sean   
    def getAppointments():
        params = event['queryStringParameters']
        try:
            id = params.get("id")
            role = params.get("role")
            getPast = params.get("getPast").lower()
        except KeyError:
            return({"status" : "failure", "message" : "missing required params"})
        
        if (role != 'vet' and role != 'owner' and role != 'pet'):
            return({"status" : "failure", "message" : "role must be vet, owner, or pet"})
        
        with conn.cursor() as cur:
            sql = "select * from appointments"
            if (role == 'vet'):
                sql = sql + " where vetid = " + str(id)
            elif (role == 'owner'):
                sql = sql + " where ownerid = " + str(id)
            else:
                sql = sql + " where petid = " + str(id)
            
            if (getPast != 'true'):
                sql = sql + ' and dt > DATE_ADD(now(), INTERVAL -5 hour)'
            
            cur.execute(sql)
            
            response = []
            for row in cur.fetchall():
                appt = {}
                appt['dt'] = str(row[3])
                appt['reason'] = row[4]
                appt['time'] = str(row[5])
                response.append(appt)
            
        
        return({"status" : "success", "response" : response})
               
    # returns all of the entries in the PET TABLE
    def getPetTable():
        #params = event['queryStringParameters']
        #query = {}
        #query['petid'] = params.get('petid')
        
        with conn.cursor() as cur:
            cur.execute("""SELECT * FROM pets """);
            response = []
            for row in cur.fetchall():
                allPetInfo = {}
                allPetInfo['Petid'] = str(row[0])
                allPetInfo['Ownerid'] = str(row[1])
                allPetInfo['Species'] = str(row[2])
                allPetInfo['Name'] = str(row[3])
                allPetInfo['Breed'] = str(row[4])
                allPetInfo['Birthday'] = str(row[5])
                response.append(allPetInfo)
        return({"status" : "success", "response" : response})
                
    # returns all of the entries FOR A GIVEN PET in the PET TABLE
    def getPetInfo():
        params = event['queryStringParameters']
        query = {}
        query['petid'] = params.get('petid')
        
        with conn.cursor() as cur:
            cur.execute("""SELECT * FROM pets WHERE petid = %s """, (query['petid']));
            response = []
            for row in cur.fetchall():
                petInfo = {}
                petInfo['Ownerid'] = str(row[1])
                petInfo['Species'] = str(row[2])
                petInfo['Name'] = str(row[3])
                petInfo['Breed'] = str(row[4])
                petInfo['Birthday'] = str(row[5])
                response.append(petInfo)
        
        if not response:
            return ({"status" : "failure"})
        else:
            return({"status" : "success", "response" : response})
    
    # returns the vitals of a pet 
    def getPetVitals():
        params = event['queryStringParameters']
        query = {}
        query['petid'] = params.get('petid')
        #print('here1')
        counter = 0
        with conn.cursor() as cur:
            cur.execute('SELECT * FROM vitals WHERE petid = {};'.format(query['petid']))
            response = []
            for row in cur.fetchall():
                vitalInfo = {}
                counter += 1
                #print('here: ' + str(counter) + ' row[1] is ' + str(row[1]))
                vitalInfo['Visit_Date'] = str(row[1])
                vitalInfo['Weight'] = str(row[2])
                vitalInfo['Age'] = str(row[3])
                vitalInfo['Heart_Rate'] = str(row[4])
                vitalInfo['Temperature'] = str(row[5])
                response.append(vitalInfo)
        
        if not response:
            return ({"status" : "failure"})
        else:
            return({"status" : "success", "response" : response})
        
                
    # Sean
    def modifyAccount():
        with conn.cursor() as cur:
            try:
                token = json.loads(event['body'])['token']
            except KeyError:
                return({"status": "failure", "message": "Token parameter missing"})
                
            try:
                userpass = decode(token).split(":")
                email = userpass[0]
                password = userpass[1]
                    
                user = {}
                #details = {}
                body = json.loads(event['body'])
                user["email"] = body["email"]
                user["password"] = body["password"]
                user["role"] = body["role"]
                user["name"] = body["name"]
                user["phone"] = body["phone"]
                user["address"] = body["address"]
                #details["firstAndLast"] = body["firstAndLast"]
                #details["phoneNum"] = body["phoneNum"]
                #details["address"] = body["address"]
                #details["carcolor"] = body["carcolor"]
                # except UnicodeDecodeError e:
                #     return({"status": "failure", "message": "Error decoding token"})
                
            except KeyError:
                return({"status": "failure", "message": "Missing required parameters"})
                    
            user_list = []
            for item in user.items():
                if str(item[1]) != 'Null':
                    user_list.append("{} = \'{}\'".format(item[0], item[1]))
                
            user_set = ", ".join(user_list)
                
            #detail_list = []
            #for item in details.items():
            #    if str(item[1]) != 'Null':
            #        detail_list.append("{} = \'{}\'".format(item[0], item[1]))
            #detail_set = ', '.join(detail_list)
                
            cur.execute("""select id into @id from users
                               where email = '{}' and password = '{}'""".format(email, password))
            if (len(user_list) != 0):
                cur.execute("""update users
                                  set {}
                                  where id = @id""".format(user_set))
            #if (len(detail_list) != 0):
            #     cur.execute("""update userdetails
            #                      set {}
            #                      where id = @id""".format(detail_set))
            conn.commit()
                
            if (user["email"] != 'Null'):
                email = user["email"]
            if (user['password'] != 'Null'):
                password = user["password"]
            cur.execute("select email, password from users where email = '{}' and password = '{}'".format(email, password))
            return({"status": "success", "token": encode(email + ":" + password)})
        
               
    # Sean 
    def addPrescriptions():
        try:
            params = json.loads(event['body'])
            petid = params['petid']
            ownerid = params['ownerid']
            prescription_name = params['prescription_name']
            dosage = params['dosage']
            dt_start = params['dt_start']
            dt_end = params['dt_end']
        except:
            return({"status": "failure", "message": "Missing required parameters"})
        
        print('1')
        with conn.cursor() as cur:
            cur.execute("""INSERT INTO prescriptions (petid, ownerid, prescription_name, dosage, dt_start, dt_end) VALUES (%s, %s, %s, %s, %s, %s)""",(petid, ownerid, prescription_name, dosage, dt_start, dt_end))
            conn.commit()
            
        print('2')
        return({"status" : "sucess", "message": "sucessfully added prescription"})
    
    # Thomas
    def addPet():
        try:
            params = json.loads(event['body'])
            species = params['species']
            name = params['name']
            breed = params['breed']
            birthday = params['birthday']
            ownerid = params['ownerid']
        except:
            return({"status": "failure", "message": "Missing required parameters"})
        
        
        with conn.cursor() as cur:
            sql = """insert into pets (ownerid, species, name, breed, birthday)
                 values (%s, %s, %s, %s, %s)"""
                 
            # Check for foreign key constraint (i.e ownerid exists in user table)
            try:
                cur.execute(sql, (ownerid, species, name, breed, birthday))
            except pymysql.err.IntegrityError as e:
                return({"status": "failure", "message": "Owner key does not exist"})
            cur.execute("select last_insert_id()")
            petid = cur.fetchone()[0]
            conn.commit()
        return({"status" : "success", "petid" : petid, "name": name})
    
    def addVitals():
        try:
            params = json.loads(event['body'])
            petid = params['petid']
            date = params['date']
            weight = params['weight']
            age = params['age']
            heartrate = params['heartrate']
            temperature = params['temperature']
        except:
            return({"status": "failure", "message": "Missing required parameters"})
            
        with conn.cursor() as cur:
            sql = """insert into vitals (petid, dt, weight, age, heartrate, temperature)
                 values (%s, %s, %s, %s, %s, %s)"""
            cur.execute(sql, (petid, date, weight, age, heartrate, temperature))
            conn.commit()
        return({"status" : "success", "message": "Vitals for petid " + str(petid) + " added"})
        
    # Thomas  
    def modifyPrescriptions():
        
        return({"status" : "incomplete"})
        
    # Thomas
    def modifyAppointment():
        
        return({"status" : "incomplete"})
        
    # Thomas
    def deletePet():
        try:
            petid = json.loads(event['body'])["petid"]
        except KeyError:
            return({"status": "failure", "message": "Missing petid"})
        with conn.cursor() as cur:
            cur.execute("delete from pets where petid = %s", petid)
            cur.execute("select row_count()")
            affected = cur.fetchone()
            conn.commit()
        if affected[0] != 0:
            return({"status": "success", "message": "pet with petid " + str(petid) + " removed"})
        else: 
            return({"status": "failure", "message": "no pet removed"})
        
    # Thomas
    def deletePrescription():
        
        return({"status" : "incomplete"})
        
    # Thomas
    def deleteAppointment():
        
        return({"status" : "incomplete"})
        
    # Thomas
    def deleteAccount():
        
        return({"status" : "incomplete"})
    
    # Thomas
    def addAppointment():
        params = json.loads(event['body'])
        try:
            ownerid = params.get("ownerid")
            petid = params.get("petid")
            vetid = params.get("vetid")
            dt = params.get("dt")
            reason = params.get("reason")
            time = params.get("time")
        except KeyError:
            return({"status": "failure", "message:" : "missing required params"})
            
        with conn.cursor() as cur:
            sql = """insert into appointments (ownerid, petid, vetid, dt, reason, time)
                     values (%s, %s, %s, %s, %s, %s)"""
            cur.execute(sql, (ownerid, petid, vetid, dt, reason, time))
            conn.commit()
        return({"status" : "success", "message" : "Appointment added for ownerid " + str(ownerid) + " with vetid " + str(vetid)}) 
    
    def getWeather():
        params = event['queryStringParameters']
        city = params.get('city') or "madison"
        state = params.get('state') or "wi"
        country = params.get('country') or "us"
        res = requests.get("https://api.openweathermap.org/data/2.5/weather?q=%7B%7D,%7B%7D,%7B%7D&appid=***&units=imperial%22.format(city, country, state))
        weather = json.loads(res.content)['weather'][0]
        temp = json.loads(res.content)['main']['temp']
        desc = weather['main']
        return({"status": "success", "temp": temp, "description": desc})
    
    def getPets():
        params = event['queryStringParameters']
        name = params.get('name') or None
        if (name == None):
            return({"status": "failure", "response": "name parameter missing"})
        with conn.cursor() as cur:
            sql = """select * from users where LOWER(name) = LOWER(\'{}\')""".format(name)
            cur.execute(sql)
            if (cur.rowcount == 0):
                return({"status" : "failure", "response": name + " not found"})
            id = cur.fetchone()[0]
            if not id:
                return ({"status" : "failure"})
            sql = """select * from pets where ownerid = %s"""
            cur.execute(sql, (id))
            pets = []
            for row in cur.fetchall():
                pet = {}
                pet["petid"] = row[0]
                pet['name'] = row[3]
                pets.append(pet)
            conn.commit()
        if not pets:
            return ({"status" : "failure"})
        else:
            return({"status" : "success", "response" : pets})
        
    
    # Add new functions to this dict and def them above 
    switcher = {
            "addAccount": addAccount,
            "validate": validate,
            "getSicknesses": getSicknesses,
            "getPrescriptions": getPrescriptions,
            "getAppointments": getAppointments,
            "getPetTable": getPetTable, # this should be unnecessary
            "getPetInfo": getPetInfo,
            "getPetVitals": getPetVitals,
            "modifyAccount": modifyAccount,
            "addPrescriptions": addPrescriptions,
            "addAppointment": addAppointment,
            "addPet": addPet,
            "addVitals": addVitals,
            "modifyPrescriptions": modifyPrescriptions,
            "modifyAppointment": modifyAppointment,
            "deletePet": deletePet,
            "deletePrescription": deletePrescription,
            "deleteAppointment": deleteAppointment,
            "deleteAccount": deleteAccount,
            'getWeather': getWeather,
            'getPets': getPets
        }
    
    # Choose what function to enter
    op_func = switcher.get(op, lambda : "invalid operation")
    response = op_func()
    conn.close()
    return {
        'statusCode': 200,
        'body': json.dumps(response)
    }

    
    
