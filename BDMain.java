package com.example.myapplication4;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context; 
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity {
 EditText reg, name, marks;
 Button addb, vb, vab, delb, ub;
 SQLiteDatabase db;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_main);
 reg = findViewById(R.id.editText);
 name = findViewById(R.id.editText2);
 marks = findViewById(R.id.editText3);
 addb = findViewById(R.id.button);
 vb = findViewById(R.id.button2);
 vab = findViewById(R.id.button3);
 delb = findViewById(R.id.button4);
 ub = findViewById(R.id.button5);
 // Create database
 db = openOrCreateDatabase("Student", Context.MODE_PRIVATE, null);
 db.execSQL("CREATE TABLE IF NOT EXISTS student(RegNo VARCHAR, Name VARCHAR, Mark
VARCHAR);");
 // Add record
 addb.setOnClickListener(v -> {
 if (reg.getText().toString().trim().isEmpty() ||
 name.getText().toString().trim().isEmpty() ||
 marks.getText().toString().trim().isEmpty()) {
 showMessage("Error", "Please enter all values");
 return;
 }
 db.execSQL("INSERT INTO student VALUES('" + reg.getText() + "','" +
 name.getText() + "','" + marks.getText() + "');");
 showMessage("Success", "Record added successfully");
 clearText();
 });
 // Delete record
 delb.setOnClickListener(v -> {
 if (reg.getText().toString().trim().isEmpty()) {
showMessage("Error", "Please enter Reg. No.");
 return;
 }
 Cursor c = db.rawQuery("SELECT * FROM student WHERE RegNo='" + reg.getText() + "'", null);
 if (c.moveToFirst()) {
 db.execSQL("DELETE FROM student WHERE RegNo='" + reg.getText() + "'");
 showMessage("Success", "Record Deleted");
 } else {
 showMessage("Error", "Invalid Reg. No.");
 }
 clearText();
 });
 // Update record
 ub.setOnClickListener(v -> {
 if (reg.getText().toString().trim().isEmpty()) {
 showMessage("Error", "Please enter Reg. No.");
 return;
 }
 Cursor c = db.rawQuery("SELECT * FROM student WHERE RegNo='" + reg.getText() + "'", null);
 if (c.moveToFirst()) {
 db.execSQL("UPDATE student SET Name='" + name.getText() +
 "', Mark='" + marks.getText() +
 "' WHERE RegNo='" + reg.getText() + "'");
 showMessage("Success", "Record Modified");
 } else {
 showMessage("Error", "Invalid Reg. No.");
 }
 clearText();
 });
 // View single record
 vb.setOnClickListener(v -> {
 if (reg.getText().toString().trim().isEmpty()) {
 showMessage("Error", "Please enter Reg. No.");
 return;
 }
 Cursor c = db.rawQuery("SELECT * FROM student WHERE RegNo='" + reg.getText() + "'", null);
 if (c.moveToFirst()) {
 name.setText(c.getString(1));
 marks.setText(c.getString(2));
 } else {
 showMessage("Error", "Invalid Reg. No.");
 clearText();
 }
 });
 // View all records
 vab.setOnClickListener(v -> {
 Cursor c = db.rawQuery("SELECT * FROM student", null);
 if (c.getCount() == 0) {
 showMessage("Error", "No records found"); 
return;
 }
 StringBuilder buffer = new StringBuilder();
 while (c.moveToNext()) {
 buffer.append("Reg. No : ").append(c.getString(0)).append("\n");
 buffer.append("Name : ").append(c.getString(1)).append("\n");
 buffer.append("Mark : ").append(c.getString(2)).append("\n\n");
 }
 showMessage("Student Details", buffer.toString());
 });
 }
 // Alert dialog
 public void showMessage(String title, String message) {
 AlertDialog.Builder builder = new AlertDialog.Builder(this);
 builder.setCancelable(true);
 builder.setTitle(title);
 builder.setMessage(message);
 builder.show();
 }
 // Clear text fields
 public void clearText() {
 reg.setText("");
 name.setText("");
 marks.setText("");
 reg.requestFocus();
 } 
  
