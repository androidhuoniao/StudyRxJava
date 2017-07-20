package com.grass.studyrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.grass.studyrxjava.base.ITestCace;
import com.grass.studyrxjava.data.TestCaseStore;
import com.grass.studyrxjava.view.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRv = (RecyclerView) findViewById(R.id.recyclerView);
        mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRv.addItemDecoration(new LinearItemDecoration(this,LinearLayoutManager.VERTICAL));
        mRv.setAdapter(new TestCaseAdapter(TestCaseStore.STORE));
    }

    class TestCaseAdapter extends RecyclerView.Adapter<TestCaseViewHolder> {
        private List<ITestCace> mList = new ArrayList<>();

        public TestCaseAdapter(List<ITestCace> list) {
            mList = list;
        }

        @Override
        public TestCaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_test_case, parent, false);
            return new TestCaseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TestCaseViewHolder holder, int position) {
            final ITestCace item = mList.get(position);
            holder.mInfoTv.setText(item.getTestName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
                    item.doOnClick();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }


    class TestCaseViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
        public TextView mInfoTv;

        public TestCaseViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mInfoTv = (TextView) itemView.findViewById(R.id.info_text);
        }
    }
}
