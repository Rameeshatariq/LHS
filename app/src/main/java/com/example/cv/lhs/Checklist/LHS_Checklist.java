package com.example.cv.lhs.Checklist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cv.lhs.Lister;
import com.example.cv.lhs.R;

public class LHS_Checklist extends AppCompatActivity {
    Context ctx= LHS_Checklist.this;
    String activity_id,activity_month;
    RelativeLayout rl_q1,rl_q2,rl_q3,rl_q4,rl_q5,rl_q6,rl_q7,rl_q8,rl_q9,rl_q10,rl_q11,rl_q12,rl_q13,rl_q14,rl_q15,rl_q16,rl_q17;
    TextView checklist_q1, checklist_q2,checklist_q3,checklist_q4,checklist_q5,checklist_q6,checklist_q7,checklist_q8,checklist_q9,
            checklist_q10,checklist_q11,checklist_q12,checklist_q13,checklist_q14,checklist_q15,checklist_q16,checklist_q17;
    TextView count_q1, count_q2, count_q3, count_q4, count_q5, count_q6, count_q7,count_q8,count_q9,count_q10,count_q11,count_q12,count_q13,
            count_q14,count_q15,count_q16,count_q17;
    TextView count_no_q1, count_no_q2, count_no_q3, count_no_q4, count_no_q5, count_no_q6,count_no_q7,count_no_q8,count_no_q9,count_no_q10,count_no_q11,
            count_no_q12,count_no_q13,count_no_q14,count_no_q15,count_no_q16,count_no_q17;
    TextView count_na_q1, count_na_q2, count_na_q3, count_na_q4, count_na_q5, count_na_q6, count_na_q7,count_na_q8,count_na_q9,count_na_q10,count_na_q11,
            count_na_q12,count_na_q13,count_na_q14,count_na_q15,count_na_q16,count_na_q17;
    String  q1_count[][], q2_count[][], q3_count[][], q4_count[][], q5_count[][], q6_count[][], q7_count[][],q8_count[][],q9_count[][],q10_count[][],
    q11_count[][],q12_count[][],q13_count[][],q14_count[][],q15_count[][],q16_count[][],q17_count[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lhs__checklist);

        activity_id=getIntent().getExtras().getString("activity_id");
        activity_month=getIntent().getExtras().getString("activity_month");

        //Relative layout
        rl_q1=(RelativeLayout)findViewById(R.id.rl_q1);
        rl_q2=(RelativeLayout)findViewById(R.id.rl_q2);
        rl_q3=(RelativeLayout)findViewById(R.id.rl_q3);
        rl_q4=(RelativeLayout)findViewById(R.id.rl_q4);
        rl_q5=(RelativeLayout)findViewById(R.id.rl_q5);
        rl_q6=(RelativeLayout)findViewById(R.id.rl_q6);
        rl_q7=(RelativeLayout)findViewById(R.id.rl_q7);
        rl_q8=(RelativeLayout)findViewById(R.id.rl_q8);
        rl_q9=(RelativeLayout)findViewById(R.id.rl_q9);
        rl_q10=(RelativeLayout)findViewById(R.id.rl_q10);
        rl_q11=(RelativeLayout)findViewById(R.id.rl_q11);
        rl_q12=(RelativeLayout)findViewById(R.id.rl_q12);
        rl_q13=(RelativeLayout)findViewById(R.id.rl_q13);
        rl_q14=(RelativeLayout)findViewById(R.id.rl_q14);
        rl_q15=(RelativeLayout)findViewById(R.id.rl_q15);
        rl_q16=(RelativeLayout)findViewById(R.id.rl_q16);
        rl_q17=(RelativeLayout)findViewById(R.id.rl_q17);

        //Textview
        checklist_q1=(TextView)findViewById(R.id.checklist_q1);

        count_q1= (TextView)findViewById(R.id.count_q1);
        count_q2= (TextView)findViewById(R.id.count_q2);
        count_q3= (TextView)findViewById(R.id.count_q3);
        count_q4= (TextView)findViewById(R.id.count_q4);
        count_q5= (TextView)findViewById(R.id.count_q5);
        count_q6= (TextView)findViewById(R.id.count_q6);
        count_q7= (TextView)findViewById(R.id.count_q7);
        count_q8= (TextView)findViewById(R.id.count_q8);
        count_q9= (TextView)findViewById(R.id.count_q9);
        count_q10= (TextView)findViewById(R.id.count_q10);
        count_q11= (TextView)findViewById(R.id.count_q11);
        count_q12= (TextView)findViewById(R.id.count_q12);
        count_q13= (TextView)findViewById(R.id.count_q13);
        count_q14= (TextView)findViewById(R.id.count_q14);
        count_q15= (TextView)findViewById(R.id.count_q15);
        count_q16= (TextView)findViewById(R.id.count_q16);
        count_q17= (TextView)findViewById(R.id.count_q17);


        count_no_q1= (TextView)findViewById(R.id.no_count_q1);
        count_no_q2= (TextView)findViewById(R.id.no_count_q2);
        count_no_q3= (TextView)findViewById(R.id.no_count_q3);
        count_no_q4= (TextView)findViewById(R.id.no_count_q4);
        count_no_q5= (TextView)findViewById(R.id.no_count_q5);
        count_no_q6= (TextView)findViewById(R.id.no_count_q6);
        count_no_q7= (TextView)findViewById(R.id.no_count_q7);
        count_no_q8 = (TextView)findViewById(R.id.no_count_q8);
        count_no_q9= (TextView)findViewById(R.id.no_count_q9);
        count_no_q10= (TextView)findViewById(R.id.no_count_q10);
        count_no_q11= (TextView)findViewById(R.id.no_count_q11);
        count_no_q12= (TextView)findViewById(R.id.no_count_q12);
        count_no_q13= (TextView)findViewById(R.id.no_count_q13);
        count_no_q14= (TextView)findViewById(R.id.no_count_q14);
        count_no_q15= (TextView)findViewById(R.id.no_count_q15);
        count_no_q16= (TextView)findViewById(R.id.no_count_q16);
        count_no_q17= (TextView)findViewById(R.id.no_count_q17);


        count_na_q1= (TextView)findViewById(R.id.na_count_q1);
        count_na_q2= (TextView)findViewById(R.id.na_count_q2);
        count_na_q3= (TextView)findViewById(R.id.na_count_q3);
        count_na_q4= (TextView)findViewById(R.id.na_count_q4);
        count_na_q5= (TextView)findViewById(R.id.na_count_q5);
        count_na_q6= (TextView)findViewById(R.id.na_count_q6);
        count_na_q7= (TextView)findViewById(R.id.na_count_q7);
        count_na_q8 = (TextView)findViewById(R.id.na_count_q8);
        count_na_q9= (TextView)findViewById(R.id.na_count_q9);
        count_na_q10= (TextView)findViewById(R.id.na_count_q10);
        count_na_q11= (TextView)findViewById(R.id.na_count_q11);
        count_na_q12= (TextView)findViewById(R.id.na_count_q12);
        count_na_q13= (TextView)findViewById(R.id.na_count_q13);
        count_na_q14= (TextView)findViewById(R.id.na_count_q14);
        count_na_q15= (TextView)findViewById(R.id.na_count_q15);
        count_na_q16= (TextView)findViewById(R.id.na_count_q16);
        count_na_q17= (TextView)findViewById(R.id.na_count_q17);

        rl_q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LHS_Checklist.this, Checklist_Q1.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month",activity_month);
                startActivity(intent);
            }
        });
        rl_q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LHS_Checklist.this, Checklist_Q2.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month",activity_month);
                startActivity(intent);
            }
        });
        rl_q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LHS_Checklist.this, Checklist_Q3.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month",activity_month);
                startActivity(intent);
            }
        });
        rl_q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LHS_Checklist.this, Checklist_Q4.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month",activity_month);
                startActivity(intent);
            }
        });
        rl_q5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LHS_Checklist.this, Checklist_Q5.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month",activity_month);
                startActivity(intent);
            }
        });
        rl_q6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LHS_Checklist.this, Checklist_Q6.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month",activity_month);
                startActivity(intent);
            }
        });
        rl_q7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LHS_Checklist.this, Checklist_Q7.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month",activity_month);
                startActivity(intent);
            }
        });
        rl_q8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LHS_Checklist.this, Checklist_Q8.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month",activity_month);
                startActivity(intent);
            }
        });
        rl_q9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LHS_Checklist.this, Checklist_Q9.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month",activity_month);
                startActivity(intent);
            }
        });
        rl_q10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LHS_Checklist.this, Checklist_Q10.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month",activity_month);
                startActivity(intent);
            }
        });
        rl_q11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LHS_Checklist.this, Checklist_Q11.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month",activity_month);
                startActivity(intent);
            }
        });
        rl_q12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LHS_Checklist.this, Checklist_Q12.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month",activity_month);
                startActivity(intent);
            }
        });
        rl_q13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LHS_Checklist.this, Checklist_Q13.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month",activity_month);
                startActivity(intent);
            }
        });
        rl_q14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LHS_Checklist.this, Checklist_Q14.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month",activity_month);
                startActivity(intent);
            }
        });
        rl_q15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LHS_Checklist.this, Checklist_Q15.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month",activity_month);
                startActivity(intent);
            }
        });
        rl_q16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LHS_Checklist.this, Checklist_Q16.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month",activity_month);
                startActivity(intent);
            }
        });
        rl_q17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LHS_Checklist.this, Checklist_Q17.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month",activity_month);
                startActivity(intent);
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        Lister ls= new Lister(ctx);
        ls.createAndOpenDB();

        try{
            q1_count=ls.executeReader("Select question1_count, question1_no_count, question1_na_count from Question1 where activity_id = '"+activity_id+"' ");
            for (int i=0; i<q1_count.length; i++){
                count_q1.setText(q1_count[0][0]);
                count_no_q1.setText(q1_count[0][1]);
                count_na_q1.setText(q1_count[0][2]);
            }
        }
        catch (Exception e){
            //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
            count_q1.setText("0");
            count_no_q1.setText("0");
            count_na_q1.setText("0");
        }

        try{
            q2_count=ls.executeReader("Select question2_count, question2_no_count, question2_na_count from Question2 where activity_id = '"+activity_id+"' ");
            for (int i=0; i<q2_count.length; i++){
                count_q2.setText(q2_count[0][0]);
                count_no_q2.setText(q2_count[0][1]);
                count_na_q2.setText(q2_count[0][2]);
            }
        }
        catch (Exception e){
            //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
            count_q2.setText("0");
            count_no_q2.setText("0");
            count_na_q2.setText("0");
        }

        try{
            q3_count=ls.executeReader("Select question3_count, question3_no_count, question3_na_count from Question3 where activity_id = '"+activity_id+"' ");
            for (int i=0; i<q3_count.length; i++){
                count_q3.setText(q3_count[0][0]);
                count_no_q3.setText(q3_count[0][1]);
                count_na_q3.setText(q3_count[0][2]);
            }
        }
        catch (Exception e){
            //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
            count_q3.setText("0");
            count_no_q3.setText("0");
            count_na_q3.setText("0");
        }

        try{
            q4_count=ls.executeReader("Select question4_count, question4_no_count, question4_na_count from Question4 where activity_id = '"+activity_id+"' ");
            for (int i=0; i<q4_count.length; i++){
                count_q4.setText(q4_count[0][0]);
                count_no_q4.setText(q4_count[0][1]);
                count_na_q4.setText(q4_count[0][2]);
            }
        }
        catch (Exception e){
            //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
            count_q4.setText("0");
            count_no_q4.setText("0");
            count_na_q4.setText("0");
        }

        try{
            q5_count=ls.executeReader("Select question5_count, question5_no_count, question5_na_count from Question5 where activity_id = '"+activity_id+"' ");
            for (int i=0; i<q5_count.length; i++){
                count_q5.setText(q5_count[0][0]);
                count_no_q5.setText(q5_count[0][1]);
                count_na_q5.setText(q5_count[0][2]);
            }
        }
        catch (Exception e){
            //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
            count_q5.setText("0");
            count_no_q5.setText("0");
            count_na_q5.setText("0");
        }

        try{
            q6_count=ls.executeReader("Select question6_count, question6_no_count, question6_na_count from Question6 where activity_id = '"+activity_id+"' ");
            for (int i=0; i<q6_count.length; i++){
                count_q6.setText(q6_count[0][0]);
                count_no_q6.setText(q6_count[0][1]);
                count_na_q6.setText(q6_count[0][2]);
            }
        }
        catch (Exception e){
            //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
            count_q6.setText("0");
            count_no_q6.setText("0");
            count_na_q6.setText("0");
        }

        try{
            q7_count=ls.executeReader("Select question7_count, question7_no_count, question7_na_count from Question7 where activity_id = '"+activity_id+"' ");
            for (int i=0; i<q7_count.length; i++){
                count_q7.setText(q7_count[0][0]);
                count_no_q7.setText(q7_count[0][1]);
                count_na_q7.setText(q7_count[0][2]);
            }
        }
        catch (Exception e){
            //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
            count_q7.setText("0");
            count_no_q7.setText("0");
            count_na_q7.setText("0");
        }

        try{
            q8_count=ls.executeReader("Select question8_count, question8_no_count, question8_na_count from Question8 where activity_id = '"+activity_id+"' ");
            for (int i=0; i<q8_count.length; i++){
                count_q8.setText(q8_count[0][0]);
                count_no_q8.setText(q8_count[0][1]);
                count_na_q8.setText(q8_count[0][2]);
            }
        }
        catch (Exception e){
            //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
            count_q8.setText("0");
            count_no_q8.setText("0");
            count_na_q8.setText("0");
        }

        try{
            q9_count=ls.executeReader("Select question9_count, question9_no_count, question9_na_count from Question9 where activity_id = '"+activity_id+"' ");
            for (int i=0; i<q9_count.length; i++){
                count_q9.setText(q9_count[0][0]);
                count_no_q9.setText(q9_count[0][1]);
                count_na_q9.setText(q9_count[0][2]);
            }
        }
        catch (Exception e){
            //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
            count_q9.setText("0");
            count_no_q9.setText("0");
            count_na_q9.setText("0");
        }

        try{
            q10_count=ls.executeReader("Select question10_count, question10_no_count, question10_na_count from Question10 where activity_id = '"+activity_id+"' ");
            for (int i=0; i<q10_count.length; i++){
                count_q10.setText(q10_count[0][0]);
                count_no_q10.setText(q10_count[0][1]);
                count_na_q10.setText(q10_count[0][2]);
            }
        }
        catch (Exception e){
            //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
            count_q10.setText("0");
            count_no_q10.setText("0");
            count_na_q10.setText("0");
        }

        try{
            q11_count=ls.executeReader("Select question11_count, question11_no_count, question11_na_count from Question11 where activity_id = '"+activity_id+"' ");
            for (int i=0; i<q11_count.length; i++){
                count_q11.setText(q11_count[0][0]);
                count_no_q11.setText(q11_count[0][1]);
                count_na_q11.setText(q11_count[0][2]);
            }
        }
        catch (Exception e){
            //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
            count_q11.setText("0");
            count_no_q11.setText("0");
            count_na_q11.setText("0");
        }

        try{
            q12_count=ls.executeReader("Select question12_count, question12_no_count, question12_na_count from Question12 where activity_id = '"+activity_id+"' ");
            for (int i=0; i<q12_count.length; i++){
                count_q12.setText(q12_count[0][0]);
                count_no_q12.setText(q12_count[0][1]);
                count_na_q12.setText(q12_count[0][2]);
            }
        }
        catch (Exception e){
            //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
            count_q12.setText("0");
            count_no_q12.setText("0");
            count_na_q12.setText("0");
        }

        try{
            q13_count=ls.executeReader("Select question13_count, question13_no_count, question13_na_count from Question13 where activity_id = '"+activity_id+"' ");
            for (int i=0; i<q13_count.length; i++){
                count_q13.setText(q13_count[0][0]);
                count_no_q13.setText(q13_count[0][1]);
                count_na_q13.setText(q13_count[0][2]);
            }
        }
        catch (Exception e){
            //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
            count_q13.setText("0");
            count_no_q13.setText("0");
            count_na_q13.setText("0");
        }

        try{
            q14_count=ls.executeReader("Select question14_count, question14_no_count, question14_na_count from Question14 where activity_id = '"+activity_id+"' ");
            for (int i=0; i<q14_count.length; i++){
                count_q14.setText(q14_count[0][0]);
                count_no_q14.setText(q14_count[0][1]);
                count_na_q14.setText(q14_count[0][2]);
            }
        }
        catch (Exception e){
            //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
            count_q14.setText("0");
            count_no_q14.setText("0");
            count_na_q14.setText("0");
        }
        try{
            q15_count=ls.executeReader("Select question15_count, question15_no_count, question15_na_count from Question15 where activity_id = '"+activity_id+"' ");
            for (int i=0; i<q15_count.length; i++){
                count_q15.setText(q15_count[0][0]);
                count_no_q15.setText(q15_count[0][1]);
                count_na_q15.setText(q15_count[0][2]);
            }
        }
        catch (Exception e){
            //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
            count_q15.setText("0");
            count_no_q15.setText("0");
            count_na_q15.setText("0");
        }

        try{
            q16_count=ls.executeReader("Select question16_count, question16_no_count, question16_na_count from Question16 where activity_id = '"+activity_id+"' ");
            for (int i=0; i<q16_count.length; i++){
                count_q16.setText(q16_count[0][0]);
                count_no_q16.setText(q16_count[0][1]);
                count_na_q16.setText(q16_count[0][2]);
            }
        }
        catch (Exception e){
            //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
            count_q16.setText("0");
            count_no_q16.setText("0");
            count_na_q16.setText("0");
        }

        try{
            q17_count=ls.executeReader("Select question17_count, question17_no_count, question17_na_count from Question17 where activity_id = '"+activity_id+"' ");
            for (int i=0; i<q17_count.length; i++){
                count_q17.setText(q17_count[0][0]);
                count_no_q17.setText(q17_count[0][1]);
                count_na_q17.setText(q17_count[0][2]);
            }
        }
        catch (Exception e){
            //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
            count_q17.setText("0");
            count_no_q17.setText("0");
            count_na_q17.setText("0");
        }
    }
}
