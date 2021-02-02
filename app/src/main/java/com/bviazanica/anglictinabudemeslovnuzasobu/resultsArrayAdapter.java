package com.bviazanica.anglictinabudemeslovnuzasobu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import static android.provider.Settings.System.getString;

class resultsArrayAdapter extends ArrayAdapter<Answer> {

    private Context context;
    private ArrayList<Answer> answers;

    public resultsArrayAdapter(Context context, int resource, ArrayList<Answer> objects) {
        super(context, resource, objects);

        this.context = context;
        this.answers = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Answer answer = answers.get(position);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.test_result_item, null);

        TextView question_number = (TextView) view.findViewById(R.id.question_number);
        TextView answer_phrase = (TextView) view.findViewById(R.id.answer_phrase);
        TextView your_answer = (TextView) view.findViewById(R.id.your_answer);
        TextView right_answer = (TextView) view.findViewById(R.id.right_answer);
        String ya = (String) your_answer.getText().toString();
        String ra = (String) right_answer.getText().toString();

        if (answer.result) {
            view.setBackgroundResource(R.color.darkgreen);
            right_answer.setText(null);
        } else {
            view.setBackgroundResource(R.color.red);
            right_answer.setText(ra + " " + answer.rightAnswer);
        }

        question_number.setText(String.valueOf(answer.questionNumber));
        answer_phrase.setText(String.valueOf(answer.phrase));
        your_answer.setText(ya + " " + answer.answer);



        return view;
    }
}
