package com.example.solit.speechsentimentanalysis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import static com.example.solit.speechsentimentanalysis.AnimationSequencer.ANIM_ALPHA;
import static com.example.solit.speechsentimentanalysis.AnimationSequencer.ANIM_ROTATION;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final AnimationSequencer.AnimationSequencerCallback mAnimCallback
                            = new AnimationSequencer.AnimationSequencerCallback() {
        @Override
        public void onAnimationEnd() {
            super.onAnimationEnd();
            Log.d(TAG, "Animation Ended");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn_move = (Button)findViewById(R.id.btn_entity);
                AnimationSequencer sequencer = new AnimationSequencer<Button>(btn_move, mAnimCallback);
                ArrayList<AnimationSequencer.AnimationSequenceParams> paramlist = new ArrayList<>();

                AnimationSequencer.AnimationSequenceParams params;

                params = sequencer.new AnimationSequenceParams();
                params.type = ANIM_ALPHA;
                params.values = new float[]{0f, 1f};
                params.time = 1000;
                paramlist.add(params);

                params = sequencer.new AnimationSequenceParams();
                params.type = ANIM_ROTATION;
                params.values = new float[]{0f, 360f};
                params.time = 3000;
                paramlist.add(params);

                sequencer.doAnimate(paramlist);
            }
        });
    }
}
