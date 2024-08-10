package com.example.gpsmaponphoto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.gpsmaponphoto.Adapters.TabLayoutAdapter;

public class StickerSettingActivity extends AppCompatActivity {


    ViewPager viewPager;
    ImageView back_setting_btn,template_main,main,maptab,weidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_setting);

        template_main = findViewById(R.id.template_main);
        main = findViewById(R.id.main);
        maptab = findViewById(R.id.maptab);
        weidget = findViewById(R.id.widget);

        back_setting_btn = findViewById(R.id.back_setting_btn);
        viewPager=findViewById(R.id.viewPager);


        size();


        main.setOnClickListener(view -> {
            template_main.setImageResource(R.drawable.template_unpress);
            main.setImageResource(R.drawable.main_press);
            maptab.setImageResource(R.drawable.maptab_unpress);
            weidget.setImageResource(R.drawable.widget_unpress);
           viewPager.setCurrentItem(1);
        });

         maptab.setOnClickListener(view -> {
             template_main.setImageResource(R.drawable.template_unpress);
             main.setImageResource(R.drawable.main_unpress);
             maptab.setImageResource(R.drawable.maptab_press);
             weidget.setImageResource(R.drawable.widget_unpress);
             viewPager.setCurrentItem(2);
         });


   weidget.setOnClickListener(view -> {
       template_main.setImageResource(R.drawable.template_unpress);
       main.setImageResource(R.drawable.main_unpress);
       maptab.setImageResource(R.drawable.maptab_press);
       weidget.setImageResource(R.drawable.widget_unpress);
       viewPager.setCurrentItem(3);
   });


        template_main.setOnClickListener(view -> {
            template_main.setImageResource(R.drawable.template_unpress);
            main.setImageResource(R.drawable.main_unpress);
            maptab.setImageResource(R.drawable.maptab_press);
            weidget.setImageResource(R.drawable.widget_unpress);
            viewPager.setCurrentItem(0);
        });




        TabLayoutAdapter adapter = new TabLayoutAdapter(this,getSupportFragmentManager(),4);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position){
                    case 0:
                    default:
                        template_main.setImageResource(R.drawable.template_press);
                        main.setImageResource(R.drawable.main_unpress);
                        maptab.setImageResource(R.drawable.maptab_unpress);
                        weidget.setImageResource(R.drawable.widget_unpress);
                        break;
                    case 1:
                        template_main.setImageResource(R.drawable.template_unpress);
                        main.setImageResource(R.drawable.main_press);
                        maptab.setImageResource(R.drawable.maptab_unpress);
                        weidget.setImageResource(R.drawable.widget_unpress);
                        break;
                    case 2:
                        template_main.setImageResource(R.drawable.template_unpress);
                        main.setImageResource(R.drawable.main_unpress);
                        maptab.setImageResource(R.drawable.maptab_press);
                        weidget.setImageResource(R.drawable.widget_unpress);
                        break;
                    case 3:
                        template_main.setImageResource(R.drawable.template_unpress);
                        main.setImageResource(R.drawable.main_unpress);
                        maptab.setImageResource(R.drawable.maptab_unpress);
                        weidget.setImageResource(R.drawable.widget_press);
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {



            }
        });



        back_setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void size() {
        HelperResizer.getheightandwidth(this);

        HelperResizer.setSize(template_main,270,100);
        HelperResizer.setSize(main,270,100);
        HelperResizer.setSize(maptab,270,100);
        HelperResizer.setSize(weidget,339,100);

        HelperResizer.setSize(back_setting_btn,60,65);
        HelperResizer.setPadding(back_setting_btn,10,10,10,10);
        HelperResizer.setMargin(back_setting_btn,70,0,0,0);

    }


}