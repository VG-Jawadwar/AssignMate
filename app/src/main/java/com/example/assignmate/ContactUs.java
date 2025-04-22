package com.example.assignmate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import androidx.appcompat.app.AppCompatActivity;


public class ContactUs extends AppCompatActivity {

    private ExpandableListView faqListView;
    private FAQAdapter faqAdapter;
    private List<String> faqQuestions;
    private HashMap<String, String> faqAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        faqListView = findViewById(R.id.faq_list);
        loadFAQData();
        faqAdapter = new FAQAdapter(this, faqQuestions, faqAnswers);
        faqListView.setAdapter(faqAdapter);
    }


    private void loadFAQData() {
        faqQuestions = new ArrayList<>();
        faqAnswers = new HashMap<>();

        faqQuestions.add("What is AssignMate?");
        faqAnswers.put("What is AssignMate?", "AssignMate is an assignment generator app that helps users create assignments from PDF documents.");

        faqQuestions.add("How do I generate an assignment?");
        faqAnswers.put("How do I generate an assignment?", "You can upload PDFs with questions, and AssignMate will extract and shuffle questions to generate assignments.");

        faqQuestions.add("Where are assignments stored?");
        faqAnswers.put("Where are assignments stored?", "Generated assignments are stored in your device's storage under the Documents folder.");

        faqQuestions.add("Is internet required?");
        faqAnswers.put("Is internet required?", "No, AssignMate works offline for generating assignments.");
    }

    public void github(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/VG-Jawadwar"));
        startActivity(i);

    }

    public void Instagram(View view) {
        Toast.makeText(this, "Oops! Module is Under Development!!!", Toast.LENGTH_SHORT).show();
    }

    public void twitter(View view) {
        Toast.makeText(this, "Oops! Module is Under Development!!!", Toast.LENGTH_SHORT).show();
    }

    public void Youtube(View view) {
        Toast.makeText(this, "Oops! Module is Under Development!!!", Toast.LENGTH_SHORT).show();
    }

    public void mail(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"vgjawadwar5@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "AssignMate Query: Ask Query");
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));

    }
}