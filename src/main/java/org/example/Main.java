package org.example;

import org.apache.commons.csv.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Main {
public static void main(String[] args) throws IOException {
    //read and store
    String filename = "Book1.csv";
    String[] headers = {"ID", "Nama", "Alamat"};
    ArrayList<String[]> bank = new ArrayList<>();
    Reader in = new FileReader(filename);
    CSVFormat csvFormat = CSVFormat.EXCEL.builder().setDelimiter(';').setHeader(headers).setSkipHeaderRecord(true).build();
    Iterable<CSVRecord> records = csvFormat.parse(in);

    for (CSVRecord record : records) {
        String[] temp = {record.get("ID"), record.get("Nama"), record.get("Alamat")};
        bank.add(temp);
    }

    //accept and set new data
    Scanner scanner = new Scanner(System.in);
    int size = bank.size();
    int id = Integer.parseInt(bank.get(size-1)[0]) + 1;
    boolean cond = true;
    while (cond){
        System.out.print("Nama : ");
        String name = scanner.nextLine();
        if (name.contentEquals("")) {
            break;
        }
        System.out.print("Alamat : ");
        String address = scanner.nextLine();
        String[] data = {String.valueOf(id), name, address};
        bank.add(data);
        id ++;
        System.out.println();
    }

    //write back into the file
    CSVFormat printFormat = CSVFormat.EXCEL.builder().setDelimiter(';').setHeader(headers).build();
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
         CSVPrinter printer = new CSVPrinter(writer, printFormat)) {
        for (String[] i : bank){
            try {
                printer.printRecords((Object) i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
}