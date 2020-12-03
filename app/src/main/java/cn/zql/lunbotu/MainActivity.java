package cn.zql.lunbotu;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp_Lunbo;
    private ArrayList<Integer> imagesidlist;
    private int[] imgid;
    private String[] titles;
    private boolean flag;
    private LinearLayout point_container;
    private TextView tv_lunbo_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        flag = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            vp_Lunbo.setCurrentItem(vp_Lunbo.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        flag = false;
        super.onDestroy();
    }

    private void initView() {
        vp_Lunbo = (ViewPager) findViewById(R.id.vp_Lunbo);
        imgid = new int[]{R.drawable.gg, R.drawable.hand, R.drawable.jj, R.drawable.ss};
        imagesidlist = new ArrayList<>();
        for (int i = 0; i < imgid.length; i++) {
            imagesidlist.add(imgid[i]);
        }
        MyAdapter myAdapter = new MyAdapter(this);
        vp_Lunbo.setAdapter(myAdapter);
        //标题
        tv_lunbo_title = (TextView) findViewById(R.id.tv_lunbo_title);
        titles = new String[]{"标题1","标题2","标题3","标题4"};
        //小圆点
        point_container = (LinearLayout) findViewById(R.id.point_container);
        for (int i = 0; i < imagesidlist.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            final View view = new View(this);
            view.setBackgroundResource(R.drawable.circle_white);
            if (i == 0) {
                view.setBackgroundResource(R.drawable.circle_select);
                tv_lunbo_title.setText(titles[i]);
            }
            view.setLayoutParams(params);
            point_container.addView(view);
        }
        vp_Lunbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "No.", Toast.LENGTH_SHORT).show();
            }
        });

        vp_Lunbo.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                final int newPosition = position % imagesidlist.size();
                for (int i = 0; i < imagesidlist.size(); i++) {
                    View point = point_container.getChildAt(i);
                    if (i == newPosition) {
                        point.setBackgroundResource(R.drawable.circle_select);
                    } else {
                        point.setBackgroundResource(R.drawable.circle_white);
                    }
                    tv_lunbo_title.setText(titles[newPosition]);

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private class MyAdapter extends PagerAdapter {
        Context context;

        public MyAdapter(Context context) {
            context = this.context;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            int newposition = position % imagesidlist.size();
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setBackgroundResource(imgid[newposition]);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
