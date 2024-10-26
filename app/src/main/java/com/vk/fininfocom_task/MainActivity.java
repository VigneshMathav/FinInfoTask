package com.vk.fininfocom_task;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView numberRecyclerView;
    AppCompatSpinner spinner;
    List<Integer> numbers;
    NumbersAdapter numbersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numbers = new ArrayList<>();

        numberRecyclerView = findViewById(R.id.numberRecycler);
        spinner = findViewById(R.id.ruleSelector);

        for (int i = 0; i <= 100; i++) {
            numbers.add(i);
        }

        numberRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,10));
        numbersAdapter = new NumbersAdapter(numbers,Rule.ODD);
        numberRecyclerView.setAdapter(numbersAdapter);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.rules, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Rule selectedRule = Rule.values()[position];
                numbersAdapter.setRule(selectedRule);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




    }


    public class NumbersAdapter extends RecyclerView.Adapter<NumbersAdapter.ViewHolder> {
        private List<Integer> numberslist;
        private Rule currentRule;

        public NumbersAdapter(List<Integer> numberslist, Rule currentRule) {
            this.numberslist = numberslist;
            this.currentRule = currentRule;
        }


        @NonNull
        @Override
        public NumbersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_number,parent,false);
            ViewHolder npView = new ViewHolder(view);
            return npView;
        }

        @Override
        public void onBindViewHolder(@NonNull NumbersAdapter.ViewHolder holder, int position) {

            int number = numbers.get(position);
            holder.number.setText(String.valueOf(number));

            if (shouldHighlight(number, currentRule)) {
                holder.number.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_purple));
            } else {
                holder.number.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.white));
            }
        }

        @Override
        public int getItemCount() {
            return numbers.size();
        }

        public void setRule(Rule rule) {
            currentRule = rule;
            notifyDataSetChanged();
        }

        private boolean shouldHighlight(int number, Rule rule) {
            switch (rule) {
                case ODD:
                    return number % 2 != 0;
                case EVEN:
                    return number % 2 == 0;
                case PRIME:
                    return isPrime(number);
                case FIBONACCI:
                    return isFibonacci(number);
                default:
                    return false;
            }
        }

        private boolean isPrime(int number) {
            if (number < 2) return false;
            for (int i = 2; i <= Math.sqrt(number); i++) {
                if (number % i == 0)
                    return false;
            }
            return true;
        }

        private boolean isFibonacci(int number) {
            int a = 0, b = 1;
            while (b < number) {
                int temp = b;
                b = a + b;
                a = temp;
            }
            return b == number || number == 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView number;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                number = itemView.findViewById(R.id.number);
            }
        }
    }
}