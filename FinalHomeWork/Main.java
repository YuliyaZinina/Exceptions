package FinalHomeWork;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) {
        //Получаем данные от пользователя
        String inputData = getInputData();

        //Распарсим строку в массив и посчитаем элементы
        String[] inputDataArray = inputData.split(" ");
        System.out.println(Arrays.toString(inputDataArray));
        int countExpected = 6;
        try {
            checkDataCount(inputDataArray, countExpected);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Если по количеству данные верны, проверим данные на типы
        Boolean isDataTypeCorrect = true;
        isDataTypeCorrect = isDataCorrect(inputDataArray);
        

        // Если по типу данные верны, формируем строку для записи
        String data = "";
        if (isDataTypeCorrect){
            data = arrayToString(inputDataArray);
            // Запишем данные в файл
            writeDataToFile(inputDataArray, data);
        }
        
        
        
    }

    //Запрашиваем и получаем данные от пользователя
    public static String getInputData() {
        String inputData;
        try (Scanner iScanner = new Scanner(System.in)) {
            System.out.println("Введите данные, разделив их пробелом: \n"
                        + "Фамилия, Имя, Отчество, Дата Рождения, Номер Телефона(только цифры), Пол(m/f). \n"
                        + "Например: Иванов Иван Иванович 01.01.1990 81234566890 m");
            inputData = iScanner.nextLine();
        }
        return inputData;
    }

    // Проверим данные по количеству
    // public static Boolean isDataCountCorrect(String[] inputDataArray, int countExpected) {
    //     if (inputDataArray.length == countExpected){
    //         return true;
    //     } else if (inputDataArray.length > 6){
    //         System.out.printf("Вы ввели лишние данные");
    //         return false;
    //     } else {
    //         System.out.printf("Вы ввели недостаточно данных");
    //         return false;
    //     }
    // }

    public static void checkDataCount(String[] inputDataArray, int count) throws Exception {
        if (inputDataArray.length > count){
          throw new Exception("Вы ввели лишние данные");
        }
        if (inputDataArray.length < count){
          throw new Exception("Вы ввели недостаточно данных");
        }
      }

    //Проверим типы данных
    public static Boolean isDataCorrect(String[] inputDataArray) {
        Boolean isDataCorrect = true;
        // проверим ФИО
        for (int i = 0; i<3; i++){
            boolean isLetterString = inputDataArray[i] != null &&
            inputDataArray[i].chars().allMatch(Character::isLetter);
            System.out.printf("%s - %b \n", inputDataArray[i], isLetterString);
            if (!isLetterString) {
                isDataCorrect = false;
                break;
            }
        }
        // Проверим дату рождения
        DateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(inputDataArray[3]);
            System.out.printf("%s - true \n", inputDataArray[3]);
        } catch (ParseException e) {
            isDataCorrect = false;
            System.out.println("Неверный формат даты");
        }

        // Проверим номер телефона
        try {
            Integer.parseInt(inputDataArray[4]);
            System.out.printf("%s - true \n", inputDataArray[4]);
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат номера телефона");
        }

        return isDataCorrect;

    }

    // формируем строку для записи
    public static String arrayToString(String[] inputDataArray) {
        StringBuilder sb = new StringBuilder();
        for (String s : inputDataArray) {
            // result.append("<");
            sb.append("<"+s+">");
        }
        sb.append("\n");
        String result = sb.toString();
        return result;
    } 

    // Запись данных в файл
    public static void writeDataToFile(String[] inputDataArray, String dataString) {
        // определяем объект для каталога
        String fileName = String.format("%s.txt", inputDataArray[0]);

        // определяем файл и записываем в него строку
        File file = new File(fileName);
        try {
            if (!file.exists()){ //если такого файла нет, создаём
                file.createNewFile();
            }
            try (FileWriter writer = new FileWriter(fileName, true)) { 
                writer.write(dataString); //Записываем строку в файл
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch(IOException e) {
            System.out.println("Catch exception" + e.getClass().getSimpleName());
        }
        
        
    }
}