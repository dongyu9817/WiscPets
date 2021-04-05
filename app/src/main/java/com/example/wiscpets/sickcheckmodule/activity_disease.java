package app.www.wiscpets.sickcheckmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import app.www.wiscpets.R;
import app.www.wiscpets.app.app_home;

public class activity_disease extends AppCompatActivity {
    TextView head ;
    TextView info[] = new TextView[2];
    CardView dis_content;
    ImageView im[] = new ImageView[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);
        head = findViewById(R.id.card_head);
        dis_content = findViewById(R.id.cardView);
        im[0] = findViewById(R.id.content_disease1);
        im[1] = findViewById(R.id.content_disease2);
        info[0] = findViewById(R.id.info1);
        info[1] = findViewById(R.id.info2);
        String name = getIntent().getStringExtra("name");
        String selectedType = getIntent().getStringExtra("selectedType");
        int max = getIntent().getIntExtra("max", 0);
        int[] c = getIntent().getIntArrayExtra("c");

        final String selectedTypeT = selectedType;
        int i = 0;
        if (selectedTypeT.equals("Covid")) {
            if (max == c[0] && i < 2 && max !=0) {
                head.setText("Your pet: " + name + " may have COVID-19 virus...");
                im[i].setImageDrawable(getResources().getDrawable(R.drawable.covid));
                info[i].setVisibility(View.VISIBLE);
                info[i].setMovementMethod(LinkMovementMethod.getInstance());
                info[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW); // dog skin allergy  and Cats
                        browserIntent.setData(Uri.parse("https://www.google.com/search?q=covid+symptoms+in+pets&rlz=1C1CHZL_enUS741US741&oq=covid+&aqs=chrome.1.69i59l2j69i57j0l5.5978j0j8&sourceid=chrome&ie=UTF-8"));
                        startActivity(browserIntent);
                    }
                });
            }
            else
            {
                head.setText("Your pet: " + name + " is likely have other health issues \n other than COVID-19...");
                im[i].setImageDrawable(getResources().getDrawable(R.drawable.covid_none));
            }
        } else {  
            head.setText("Your pet: " + name + " may have...");
            if (max == c[0] && i < 2 ) {
                if (selectedTypeT.equals("Dog"))
                    im[i].setImageDrawable(getResources().getDrawable(R.drawable.d1));
                else
                    im[i].setImageDrawable(getResources().getDrawable(R.drawable.d1c));
                info[i].setVisibility(View.VISIBLE);
                info[i].setMovementMethod(LinkMovementMethod.getInstance());
                info[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW); // dog skin allergy  and Cats
                        if (selectedTypeT.equals("Dog"))
                            browserIntent.setData(Uri.parse("https://www.google.com/search?rlz=1C1CHBF_en-gbUS859US859&ei=6MG8XrOqOJHYsQX1x6moBg&q=dog+skin+allergy+cause+and+prevention+tips&oq=dog+skin+allergy+cause+and+prevention+tips&gs_lcp=CgZwc3ktYWIQAzIFCAAQzQIyBQgAEM0CMgUIABDNAjoECAAQRzoICCEQFhAdEB46BQghEKABUPQpWKYuYMY3aABwAXgAgAG3AYgBvASSAQMwLjSYAQCgAQGqAQdnd3Mtd2l6&sclient=psy-ab&ved=0ahUKEwizvtGuu7LpAhURbKwKHfVjCmUQ4dUDCAw&uact=5"));
                        else
                            browserIntent.setData(Uri.parse("https://www.google.com/search?q=how+to+prevent+cat+from+getting+Kidney+Disease&rlz=1C1CHBF_en-gbUS859US859&oq=how+to+prevent++cat+from+getting+Kidney+Disease&aqs=chrome..69i57j33.1299j0j8&sourceid=chrome&ie=UTF-8"));
                        startActivity(browserIntent);
                    }
                });
                i++;
            }
            if (max == c[1] && i < 2 ) {
                if (selectedTypeT.equals("Dog"))
                    im[i].setImageDrawable(getResources().getDrawable(R.drawable.d2));
                else
                    im[i].setImageDrawable(getResources().getDrawable(R.drawable.d2c));
                info[i].setVisibility(View.VISIBLE);
                info[i].setMovementMethod(LinkMovementMethod.getInstance());
                info[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // dog Ear Infection
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        if (selectedTypeT.equals("Dog"))
                            browserIntent.setData(Uri.parse(
                                    "https://www.google.com/search?rlz=1C1CHBF_en-gbUS859US859&ei=8cG8XojaEMK6sQXY0LmoAg&q=dog+Ear+Infection++cause+and+prevention+tips&oq=dog+Ear+Infection++cause+and+prevention+tips&gs_lcp=CgZwc3ktYWIQAzoECAAQR1DglQNY4JUDYJ6jA2gAcAJ4AIABZIgBZJIBAzAuMZgBAKABAqABAaoBB2d3cy13aXo&sclient=psy-ab&ved=0ahUKEwjIls-yu7LpAhVCXawKHVhoDiUQ4dUDCAw&uact=5 Causes"));
                        else
                            browserIntent.setData(Uri.parse(
                                    "https://www.google.com/search?rlz=1C1CHBF_en-gbUS859US859&ei=4PC_XuCXAoiWtQXA3J-ABQ&q=how+to+prevent++cat+from+getting+Thyroid&oq=how+to+prevent++cat+from+getting+Thyroid&gs_lcp=CgZwc3ktYWIQAzIICCEQFhAdEB5Qp-g1WKfoNWC--jVoAHAAeACAAZUBiAGVAZIBAzAuMZgBAKABAqABAaoBB2d3cy13aXo&sclient=psy-ab&ved=0ahUKEwjgh9rJxLjpAhUIS60KHUDuB1AQ4dUDCAw&uact=5"));
                        startActivity(browserIntent);
                    }
                });
                i++;
            }

            if (max == c[2] && i < 2  ) {
                if (selectedTypeT.equals("Dog"))
                    im[i].setImageDrawable(getResources().getDrawable(R.drawable.d3));
                else
                    im[i].setImageDrawable(getResources().getDrawable(R.drawable.d3c));
                info[i].setVisibility(View.VISIBLE);
                info[i].setMovementMethod(LinkMovementMethod.getInstance());
                info[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        if (selectedTypeT.equals("Dog"))
                        browserIntent.setData(Uri.parse(
                                    "https://www.google.com/search?rlz=1C1CHBF_en-gbUS859US859&ei=y8O8XoWyNMjYsAXU9qWwAw&q=how+to+prevent++dog+from+getting+Arthritis+&oq=how+to+prevent++dog+from+getting+Arthritis+&gs_lcp=CgZwc3ktYWIQAzIICCEQFhAdEB5QzSZYzSZg9zBoAHAAeACAAWiIAWiSAQMwLjGYAQCgAQKgAQGqAQdnd3Mtd2l6&sclient=psy-ab&ved=0ahUKEwjFw_WUvbLpAhVILKwKHVR7CTYQ4dUDCAw&uact=5"));
                        else
                            browserIntent.setData(Uri.parse(
                                    "https://www.google.com/search?rlz=1C1CHBF_en-gbUS859US859&ei=s_m_Xp-2A86ItQWX34qoBg&q=how+to+prevent++cat+from+getting+Bladder+Infection&oq=how+to+prevent++cat+from+getting+Bladder+Infection&gs_lcp=CgZwc3ktYWIQAzIICCEQFhAdEB4yCAghEBYQHRAeOgQIABBHUPAfWPAfYOUpaABwAXgAgAF5iAF5kgEDMC4xmAEAoAECoAEBqgEHZ3dzLXdpeg&sclient=psy-ab&ved=0ahUKEwjf2_H-zLjpAhVORK0KHZevAmUQ4dUDCAw&uact=5"));
                        startActivity(browserIntent);
                    }
                });
                i++;
            }

            if (max == c[3] && i < 2 ) {
                if (selectedTypeT.equals("Dog"))
                    im[i].setImageDrawable(getResources().getDrawable(R.drawable.d4));
                else
                    im[i].setImageDrawable(getResources().getDrawable(R.drawable.d4c));
                info[i].setVisibility(View.VISIBLE);
                info[i].setMovementMethod(LinkMovementMethod.getInstance());
                info[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        if (selectedTypeT.equals("Dog"))
                            browserIntent.setData(Uri.parse(
                                    "https://www.google.com/search?rlz=1C1CHBF_en-gbUS859US859&ei=WaG9Xp6sJMPIsAXKk4iICQ&q=how+to+prevent++dog+from+getting+Bladder+Infection&oq=how+to+prevent++dog+from+getting+Bladder+Infection&gs_lcp=CgZwc3ktYWIQAzIFCCEQqwI6BAgAEEc6BAghEApQy40BWJeyAWClugFoAHACeACAAX2IAa0DkgEDMS4zmAEAoAECoAEBqgEHZ3dzLXdpeg&sclient=psy-ab&ved=0ahUKEwjevIW6kLTpAhVDJKwKHcoJApEQ4dUDCAw&uact=5"));
                        else
                            browserIntent.setData(Uri.parse(
                                    "https://www.google.com/search?q=how+to+prevent+cat+from+getting+Dental_Disease&rlz=1C1CHBF_en-gbUS859US859&oq=how+to+prevent++cat+from+getting+Dental_Disease&aqs=chrome..69i57.14599889j1j4&sourceid=chrome&ie=UTF-8"));
                        startActivity(browserIntent);
                    }
                });
                i++;
            }
            if (max == c[4] && i < 2  ) {
                if (selectedTypeT.equals("Dog"))
                    im[i].setImageDrawable(getResources().getDrawable(R.drawable.d5));
                else
                    im[i].setImageDrawable(getResources().getDrawable(R.drawable.d5c));
                info[i].setVisibility(View.VISIBLE);
                info[i].setMovementMethod(LinkMovementMethod.getInstance());
                info[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        if (selectedTypeT.equals("Dog"))
                            browserIntent.setData(Uri.parse(
                                    "https://www.google.com/search?rlz=1C1CHBF_en-gbUS859US859&ei=BaW9XqzRK4HmsAWL97qIDQ&q=how+to+prevent++dog+from+getting+Dental+Disease&oq=how+to+prevent++dog+from+getting+Dental+Disease&gs_lcp=CgZwc3ktYWIQAzIICCEQFhAdEB5QuiVYuiVgwS9oAHAAeACAAXmIAXmSAQMwLjGYAQCgAQKgAQGqAQdnd3Mtd2l6&sclient=psy-ab&ved=0ahUKEwjs56n6k7TpAhUBM6wKHYu7DtEQ4dUDCAw&uact=5"));
                        else
                            browserIntent.setData(Uri.parse(
                                    "https://www.google.com/search?rlz=1C1CHBF_en-gbUS859US859&ei=HarAXtLGE4mYsAX18puIDQ&q=how+to+prevent++cat+from+getting+Upset+Stomach&oq=how+to+prevent++cat+from+getting+Upset+Stomach&gs_lcp=CgZwc3ktYWIQAzIFCCEQqwIyBQghEKsCMgUIIRCrAjoECAAQR1Dq7rkBWOruuQFgh_e5AWgAcAF4AIAB0AGIAdABkgEDMi0xmAEAoAECoAEBqgEHZ3dzLXdpeg&sclient=psy-ab&ved=0ahUKEwiSyfed9bnpAhUJDKwKHXX5BtEQ4dUDCAw&uact=5"));
                        startActivity(browserIntent);
                    }
                });
                i++;
            }
            if (max == c[5] && i < 2 ) {
                if (selectedTypeT.equals("Dog"))
                    im[i].setImageDrawable(getResources().getDrawable(R.drawable.d6));
                else
                    im[i].setImageDrawable(getResources().getDrawable(R.drawable.d6c));
                info[i].setVisibility(View.VISIBLE);
                info[i].setMovementMethod(LinkMovementMethod.getInstance());
                info[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // dog Ear Infection
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        if (selectedTypeT.equals("Dog"))
                            browserIntent.setData(Uri.parse(
                                    "https://www.google.com/search?rlz=1C1CHBF_en-gbUS859US859&ei=yKa9Xor6C4OwsAXS1pHYCw&q=how+to+prevent++dog+from+getting+Upset+Stomach&oq=how+to+prevent++dog+from+getting+Upset+Stomach&gs_lcp=CgZwc3ktYWIQAzIFCCEQqwIyBQghEKsCMgUIIRCrAjoECAAQR1CwEFiuEWDkFGgAcAF4AIABiwGIAe4BkgEDMS4xmAEAoAEBqgEHZ3dzLXdpeg&sclient=psy-ab&ved=0ahUKEwiK_pDRlbTpAhUDGKwKHVJrBLsQ4dUDCAw&uact=5"));
                        else
                            browserIntent.setData(Uri.parse(
                                    "https://www.google.com/search?q=how+to+prevent+cat+from+getting+diabetes&rlz=1C1CHBF_en-gbUS859US859&oq=how+to+prevent++cat+from+getting+Diabetes&aqs=chrome.0.0l5.1415j0j8&sourceid=chrome&ie=UTF-8"));
                        startActivity(browserIntent);
                    }
                });
                i++;
            }
        }
    } // Covid check

    @Override
    public void onBackPressed() {
        finish();
        Intent tIntent = new Intent(this, app_home.class);
        startActivity(tIntent);
    }
}