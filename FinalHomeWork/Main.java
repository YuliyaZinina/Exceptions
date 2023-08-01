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
    public static void main(String[] args) throws DataCountException, DataFormatException {
        //Получаем данные от пользователя
        String inputData = getInputData();

        //Распарсим строку в массив и посчитаем элементы
        String[] inputDataArray = inputData.split(" ");
        System.out.println(Arrays.toString(inputDataArray));
        int countExpected = 6;
        checkDataCount(inputDataArray, countExpected);

        // Если по количеству данные верны, проверим данные на типы
        checkData(inputDataArray);

        // Если по типу данные верны, формируем строку для записи
        String data = arrayToString(inputDataArray);
        // Запишем данные в файл
        writeDataToFile(inputDataArray, data);
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

    public static void checkDataCount(String[] inputDataArray, int count) throws DataCountException {
        if (inputDataArray.length > count){
          throw new DataCountException("Вы ввели лишние данные");
        }
        if (inputDataArray.length < count){
          throw new DataCountException("Вы ввели недостаточно данных");
        }
    }

    public static void checkData(String[] data) throws DataFormatException {
        // проверим ФИО
        for (int i = 0; i < 3; i++) {
            boolean isLetterString = data[i] != null &&
                    data[i].chars().allMatch(Character::isLetter);
            System.out.printf("%s - %b \n", data[i], isLetterString);
            if (!isLetterString) {
                throw new DataFormatException("Неверный формат ФИО");
            }
        }
        // Проверим дату рождения
        DateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(data[3]);
            System.out.printf("%s - true \n", data[3]);
        } catch (ParseException e) {
            throw new DataFormatException("Неверный формат даты");
        }

        // Проверим номер телефона
        String regex = "^\\d{11}$";
        if (!data[4].matches(regex)) {
            throw new DataFormatException("Неверный формат номера телефона");
        }
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