package com.syh.persioncontentprovider;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author shenyonghe
 *
 * 2014-10-10
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        PersionDao2 persionDao2 = new PersionDao2(this);
//        long number = 13111111111l;
//        for (int i = 0; i < 50; i++) {
//			Persion persion = new Persion(i, "shen"+i, ""+number+i);
//			persionDao2.add(persion.getName(), persion.getNumber());
//		}
    }

}
