package com.example.assignmate;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class questions_input extends AppCompatActivity {
    ActivityResultLauncher<Intent> resultLauncher;
    EditText chapters, name, Department, sub_name;
    private Button pickPdfButton, pickPdfButton2, pickPdfButton3, pickPdfButton4, pickPdfButton5, mergepdf, back;

    private Uri pdfUri;
    private ActivityResultLauncher<Intent> pdfPickerLauncher;

    static ArrayList<String> list_path = new ArrayList<String>();

    private DatabaseHelper dbHelper;

    public void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // this function is called before text is edited
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (s == null || s.toString().trim().isEmpty()) {
                // Prevent crash when text is cleared
                return;
            }


            switch (Integer.parseInt(String.valueOf(chapters.getText()))) {
                case 0:
                    pickPdfButton.setEnabled(false);
                    pickPdfButton2.setEnabled(false);
                    pickPdfButton3.setEnabled(false);
                    pickPdfButton4.setEnabled(false);
                    pickPdfButton5.setEnabled(false);
                    break;
                case 1:
                    pickPdfButton.setEnabled(true);
                    pickPdfButton2.setEnabled(false);
                    pickPdfButton3.setEnabled(false);
                    pickPdfButton4.setEnabled(false);
                    pickPdfButton5.setEnabled(false);
                    break;
                case 2:
                    pickPdfButton.setEnabled(true);
                    pickPdfButton2.setEnabled(true);
                    pickPdfButton3.setEnabled(false);
                    pickPdfButton4.setEnabled(false);
                    pickPdfButton5.setEnabled(false);
                    break;
                case 3:
                    pickPdfButton.setEnabled(true);
                    pickPdfButton2.setEnabled(true);
                    pickPdfButton3.setEnabled(true);
                    pickPdfButton4.setEnabled(false);
                    pickPdfButton5.setEnabled(false);
                    break;
                case 4:
                    pickPdfButton.setEnabled(true);
                    pickPdfButton2.setEnabled(true);
                    pickPdfButton3.setEnabled(true);
                    pickPdfButton4.setEnabled(true);
                    pickPdfButton5.setEnabled(false);
                    break;
                case 5:
                    pickPdfButton.setEnabled(true);
                    pickPdfButton2.setEnabled(true);
                    pickPdfButton3.setEnabled(true);
                    pickPdfButton4.setEnabled(true);
                    pickPdfButton5.setEnabled(true);
                    break;
                default:
                    toastMsg("Oops! Please Enter Valid Chapters.");
                    pickPdfButton.setEnabled(false);
                    pickPdfButton2.setEnabled(false);
                    pickPdfButton3.setEnabled(false);
                    pickPdfButton4.setEnabled(false);
                    pickPdfButton5.setEnabled(false);
                    break;

            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            // this function is called after text is edited
        }
    };


    TextWatcher textWatcher_name = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // this function is called before text is edited
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (s == null || s.toString().trim().isEmpty()) {
                // Prevent crash when text is cleared
                return;
            } else {
                Department.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            // this function is called after text is edited
        }
    };

    TextWatcher textWatcher_sub = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // this function is called before text is edited
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (s == null || s.toString().trim().isEmpty()) {
                // Prevent crash when text is cleared
                return;
            } else {
                chapters.setEnabled(true);
            }


        }

        @Override
        public void afterTextChanged(Editable s) {
            // this function is called after text is edited
        }
    };

    TextWatcher textWatcher_Dept = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // this function is called before text is edited
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (s == null || s.toString().trim().isEmpty()) {
                // Prevent crash when text is cleared
                return;
            } else {
                sub_name.setEnabled(true);
            }


        }

        @Override
        public void afterTextChanged(Editable s) {
            // this function is called after text is edited
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_questions_input);


        pickPdfButton = findViewById(R.id.pick_pdf_button1);
        pickPdfButton2 = findViewById(R.id.pick_pdf_button2);
        pickPdfButton3 = findViewById(R.id.pick_pdf_button3);
        pickPdfButton4 = findViewById(R.id.pick_pdf_button4);
        pickPdfButton5 = findViewById(R.id.pick_pdf_button5);
        back = findViewById(R.id.BackButton);
        mergepdf = findViewById(R.id.merge_pdf);

        chapters = findViewById(R.id.editTextText4);
        name = findViewById(R.id.editTextText);
        sub_name = findViewById(R.id.editTextText3);
        Department = findViewById(R.id.editTextText2);


        pickPdfButton.setEnabled(false);
        pickPdfButton2.setEnabled(false);
        pickPdfButton3.setEnabled(false);
        pickPdfButton4.setEnabled(false);
        pickPdfButton5.setEnabled(false);

        chapters.addTextChangedListener(textWatcher);
        name.addTextChangedListener(textWatcher_name);
        sub_name.addTextChangedListener(textWatcher_sub);
        Department.addTextChangedListener(textWatcher_Dept);

        Department.setEnabled(false);
        sub_name.setEnabled(false);
        chapters.setEnabled(false);

        dbHelper = new DatabaseHelper(this);

        pickPdfButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPdfPicker();

            }
        });

        pickPdfButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPdfPicker();

            }
        });

        pickPdfButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPdfPicker();

            }
        });

        pickPdfButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPdfPicker();

            }
        });

        pickPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPdfPicker();

            }
        });

        mergepdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().trim().matches("^(\\D){2,}$") && Department.getText().toString().trim().matches("^(\\D){2,}$") && sub_name.getText().toString().trim().matches("^(\\D){2,}$")) {
                    generateAssignments();
                } else {
                    Toast.makeText(getApplicationContext(), "Oops! Please Enter Valid Details", Toast.LENGTH_SHORT).show();
                }

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_path.clear();
                startActivity(new Intent(getApplicationContext(), Home.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });


        pdfPickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            pdfUri = data.getData();
                            list_path.add(String.valueOf(pdfUri));

                            int selectedCount = list_path.size();
                            disablePickedButton(selectedCount);

                            Toast.makeText(this, "Chapter " + list_path.size() + " PDF Selected!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "No PDF selected", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void disablePickedButton(int count) {
        switch (count) {
            case 1:
                pickPdfButton.setEnabled(false);
                break;
            case 2:
                pickPdfButton2.setEnabled(false);
                break;
            case 3:
                pickPdfButton3.setEnabled(false);
                break;
            case 4:
                pickPdfButton4.setEnabled(false);
                break;
            case 5:
                pickPdfButton5.setEnabled(false);
                break;
        }
    }


    void generateAssignments() {

        if (!list_path.isEmpty()) {
            try {
                List<String> allQuestions = new ArrayList<>();
                int i = 1;
                for (String uriString : list_path) {
                    Uri fileUri = Uri.parse(uriString);
                    List<String> questions = extractQuestionsFromPdf(fileUri);
                    allQuestions.addAll(questions);
                    Collections.shuffle(allQuestions);

                    createPdfAssignment(allQuestions, sub_name.getText().toString() + " assignment_" + i + ".pdf");
                    i++;
                }


                Toast.makeText(this, "Assignments generated successfully!", Toast.LENGTH_LONG).show();

                saveAssignmentData();
                resetPage();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error generating assignments: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No PDF Selected, Generation Failed!!", Toast.LENGTH_LONG).show();
        }
    }


    private void saveAssignmentData() {
        String name_user = name.getText().toString().trim();
        String department = Department.getText().toString().trim();
        String subject = sub_name.getText().toString().trim();
        String chaptersStr = chapters.getText().toString().trim();

        if (name_user.isEmpty() || department.isEmpty() || subject.isEmpty() || chaptersStr.isEmpty()) {
            return;
        }

        int chapters = Integer.parseInt(chaptersStr);

        dbHelper.insertAssignment(name_user, department, subject, chapters, this);
    }


    private void resetPage() {
        list_path.clear();

        pickPdfButton.setEnabled(false);
        pickPdfButton2.setEnabled(false);
        pickPdfButton3.setEnabled(false);
        pickPdfButton4.setEnabled(false);
        pickPdfButton5.setEnabled(false);

        chapters.setText("");
        name.setText("");
        sub_name.setText("");
        Department.setText("");
    }

    public List<String> extractQuestionsFromPdf(Uri pdfUri) {
        List<String> questions = new ArrayList<>();
        try {
            ContentResolver contentResolver = getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(pdfUri);
            PdfReader reader = new PdfReader(inputStream);

            StringBuilder pdfText = new StringBuilder();
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {

                String pageText = PdfTextExtractor.getTextFromPage(reader, i);
                pageText = pageText.replace("\n", " ").replace("\r", " ").trim();
                pdfText.append(pageText);
            }
            reader.close();

            String[] extractedQuestions = pdfText.toString().split("\\?");
            for (String question : extractedQuestions) {
                if (question.trim().length() > 10) {
                    questions.add(question.trim() + "?");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    public void createPdfAssignment(List<String> questions, String fileName) {
        try {
            File mergedFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(mergedFile));
            document.open();

            String fontPath = "assets/Poppins-Regular.ttf";
            BaseFont poppinsBaseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font poppinsFont_head = new Font(poppinsBaseFont, 18, Font.BOLD);
            Font poppinsFont_desc = new Font(poppinsBaseFont, 14, Font.NORMAL);

            Paragraph paragraph_heading = new Paragraph(sub_name.getText().toString() + " Assignment " + fileName.replace(".pdf", "").split("_")[1] + "\n\n", poppinsFont_head);
            paragraph_heading.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph_heading);

            int count = Math.min(20, questions.size());
            for (int i = 0; i < count; i++) {

                String cleanedQuestion = questions.get(i).replace("\n", " ").trim();

                Paragraph paragraph = new Paragraph((i + 1) + ". " + cleanedQuestion, poppinsFont_desc);

                paragraph.setAlignment(Element.ALIGN_LEFT);
                document.add(paragraph);
                document.add(new Paragraph("\n"));
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openPdfPicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("application/pdf");
        pdfPickerLauncher.launch(intent);
    }
}
