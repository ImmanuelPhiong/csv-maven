package org.example;

import org.apache.commons.csv.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.Stack;

public class Main {

static class Person {
    private int id;
    private String nama;
    private String alamat;

    public Person(int id, String nama, String alamat) {
     this.id = id;
     this.nama = nama;
     this.alamat = alamat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public String toString(){
        String[] temp = {String.valueOf(id), nama, alamat};
        return String.join(";",temp);
    }
}

    private static Person createPerson(String[] metadata) {
        int id = Integer.parseInt(metadata[0]);
        String nama = metadata[1];
        String alamat = metadata[2];

        // create and return book of this metadata
        return new Person(id, nama, alamat);
    }

public static void main(String[] args) throws IOException {
    //read and store
    String filename = "Book1.csv";
    String[] headers = {"ID", "Nama", "Alamat"};
    ArrayList<Person> persons = new ArrayList<>();
    Reader in = new FileReader(filename);
    CSVFormat csvFormat = CSVFormat.EXCEL.builder().setDelimiter(';').setHeader(headers).setSkipHeaderRecord(true).build();
    Iterable<CSVRecord> records = csvFormat.parse(in);

    for (CSVRecord record : records) {
        String[] temp = {record.get("ID"), record.get("Nama"), record.get("Alamat")};
        Person person = createPerson(temp);
        persons.add(person);
    }

    //set new data
    Scanner scanner = new Scanner(System.in);


    //write back into the file
    CSVFormat printFormat = CSVFormat.EXCEL.builder().setDelimiter(',').setHeader("ID;Nama;Alamat").build();
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
         CSVPrinter printer = new CSVPrinter(writer, printFormat)) {
        for (Person i : persons){
            try {
                printer.printRecord(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
}