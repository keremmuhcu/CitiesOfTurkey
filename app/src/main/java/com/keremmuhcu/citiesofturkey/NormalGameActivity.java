package com.keremmuhcu.citiesofturkey;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.keremmuhcu.citiesofturkey.databinding.ActivityNormalGameBinding;

import java.util.ArrayList;
import java.util.Random;

public class NormalGameActivity extends AppCompatActivity {
    private ActivityNormalGameBinding binding;
    private String[][] cities = {{"ADANA", "ANTALYA", "BURDUR", "HATAY", "ISPARTA", "KAHRAMANMARAŞ", "MERSİN", "OSMANİYE"},
            {"AĞRI", "ARDAHAN", "BİNGÖL", "BİTLİS", "ELAZIĞ", "ERZİNCAN", "ERZURUM", "HAKKARİ", "IĞDIR",
                    "KARS", "MALATYA", "MUŞ", "ŞIRNAK", "TUNCELİ", "VAN"},
            {"AFYONKARAHİSAR", "AYDIN", "DENİZLİ", "İZMİR", "KÜTAHYA", "MANİSA", "MUĞLA", "UŞAK"},
            {"ADIYAMAN", "BATMAN", "DİYARBAKIR", "GAZİANTEP", "KİLİS", "MARDİN", "SİİRT", "ŞANLIURFA"},
            {"AKSARAY", "ANKARA", "ÇANKIRI", "ESKİŞEHİR", "KARAMAN", "KAYSERİ", "KIRIKKALE", "KIRŞEHİR",
                    "KONYA", "NEVŞEHİR", "NİĞDE", "SİVAS", "YOZGAT"},
            {"AMASYA", "ARTVİN", "BARTIN", "BAYBURT", "BOLU","ÇORUM", "DÜZCE", "GİRESUN", "GÜMÜŞHANE", "KARABÜK",
                    "KASTAMONU", "ORDU", "RİZE", "SAMSUN", "SİNOP", "TOKAT", "TRABZON", "ZONGULDAK"},
            {"BALIKESİR", "BİLECİK", "BURSA", "ÇANAKKALE", "EDİRNE", "İSTANBUL", "KIRKLARELİ", "KOCAELİ", "SAKARYA",
                    "TEKİRDAĞ", "YALOVA"}};

    Random random;
    int regionIx, cityIx, showingLetter, cityLength, point, totalPoint, highPoint, remainedLetter;
    TextView cityTextView, infoTextView, pointTextView, recordTextView;
    EditText answerEditText;
    Button getLetterBTN;
    ArrayList<Integer> randomIndexes;
    ArrayList<String> solvedCities;
    String[] cityLetters;
    String region, city, cityWithInfo;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNormalGameBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        cityTextView = binding.cityTextViewN;
        answerEditText = binding.answerEditTextN;
        infoTextView = binding.infoTextViewN;
        pointTextView = binding.pointTextViewN;
        recordTextView = binding.recordTextViewN;
        getLetterBTN = binding.getLetterNBTN;

        totalPoint = 0;
        preferences = this.getSharedPreferences("highScoreNormal", Context.MODE_PRIVATE);
        highPoint = preferences.getInt("highScoreNormal", 0);
        recordTextView.setText("Rekor: " + highPoint);

        randomIndexes = new ArrayList<>();
        solvedCities = new ArrayList<>();
        random = new Random();

        newCity();


    }

    public void getLetter(View view) {
        if (remainedLetter > 0) {
            int randomIndex = getRandomIndex();
            openLetter(randomIndex);
            showCity();
            remainedLetter--;
            point -= 2;
            Toast.makeText(this,"Kalan Hak: " + remainedLetter, Toast.LENGTH_SHORT).show();
        }
        if (remainedLetter == 0) {
            getLetterBTN.setEnabled(false);
        }



    }

    public void guess(View view) {

        if (answerEditText.getText().toString().toUpperCase().equals(city)) {
            totalPoint += point;
            Toast.makeText(this, "Tebrikler", Toast.LENGTH_SHORT).show();
            answerEditText.setText("");
            pointTextView.setText("PUAN: " + totalPoint);

            if (totalPoint > highPoint) {
                preferences.edit().putInt("highScoreNormal", totalPoint).apply();
                highPoint = preferences.getInt("highScoreNormal", totalPoint);
                recordTextView.setText("Rekor: " + highPoint);
            }

            if(solvedCities.size() == 81) {
                infoTextView.setText("OYUN BİTTİ");
                cityTextView.setText("");
                finishAndTryAgain();
            } else {
                getLetterBTN.setEnabled(true);
                newCity();
            }

        } else {
            Toast.makeText(this, "Yanlış Cevap", Toast.LENGTH_SHORT).show();
            totalPoint -= 6;
            pointTextView.setText("PUAN: " + totalPoint);
        }




    }

    public int getRandomIndex() {
        int indexRandom = random.nextInt(cityLength);

        if (randomIndexes.size() != 0) {
            while (!control(indexRandom)) {
                indexRandom = random.nextInt(cityLength);
            }
        }
        randomIndexes.add(indexRandom);
        return indexRandom;

    }

    public boolean control(int number) {
        boolean condition = true;
        for (int i = 0; i < randomIndexes.size(); i++) {
            if (randomIndexes.get(i) == number) {
                condition = false;
                break;
            }
        }
        return condition;
    }

    public boolean control(String word) {
        boolean condition = true;
        for (int i = 0; i < solvedCities.size(); i++) {
            if (solvedCities.get(i) == word) {
                condition = false;
                break;
            }
        }
        return condition;
    }

    public void openLetter(int randomIndex) {

        for (int j = 0; j < cityLength; j++) {
            if (cityLetters[randomIndex].equals(" _ ")) {
                cityLetters[randomIndex] = " " + city.charAt(randomIndex) + " ";
            }
        }

    }

    public void showCity() {
        cityWithInfo = "";
        for (int i = 0; i < cityLength; i++) {
            cityWithInfo += cityLetters[i];
        }

        cityTextView.setText(cityWithInfo);
    }

    public void newCity() {
        createCity();

        infoTextView.setText(region + " Bölgesi'nde Bulunuyor");

        cityLetters = new String[cityLength];
        randomIndexes = new ArrayList<>();

        for (int i = 0; i < cityLength; i++) {
            cityLetters[i] = " _ ";
        }

        if (cityLength < 5) {
            showingLetter = 1;
            point = cityLength + 4;
            remainedLetter = 1;
        } else if (cityLength < 8) {
            showingLetter = 2;
            point = cityLength + 6;
            remainedLetter = 2;
        } else {
            showingLetter = 3;
            point = cityLength + 6;
            remainedLetter = 2;
        }

        for (int i = 0; i < showingLetter; i++) {
            int randomIndex = getRandomIndex();
            openLetter(randomIndex);
        }

        showCity();
    }

    public void createCity() {
        regionIx = random.nextInt(cities.length);
        cityIx = random.nextInt(cities[regionIx].length);
        city = cities[regionIx][cityIx];
        region = getRegion(regionIx);

        if (solvedCities.size() != 0) {
            while (!control(city)) {
                regionIx = random.nextInt(cities.length);
                cityIx = random.nextInt(cities[regionIx].length);
                city = cities[regionIx][cityIx];
                region = getRegion(regionIx);
            }
        }
        solvedCities.add(city);

        cityLength = city.length();
    }

    public String getRegion(int regionIndex) {
        /*0- Akdeniz            1-Doğu Anadolu   2- Ege
          3- Güneydoğu Anadolu  4- İç Anadolu    5- Karadeniz     6- Marmara*/

        if (regionIndex == 0)
            return "Akdeniz";
        else if (regionIndex == 1)
            return "Doğu Anadolu";
        else if (regionIndex == 2)
            return "Ege";
        else if (regionIndex == 3)
            return "Güneydoğu Anadolu";
        else if (regionIndex == 4)
            return "İç Anadolu";
        else if (regionIndex == 5)
            return "Karadeniz";
        else
            return "Marmara";
    }

    public void finishAndTryAgain() {
        AlertDialog.Builder alert = new AlertDialog.Builder(NormalGameActivity.this);
        alert.setTitle("Tekrar Oyna");
        alert.setMessage("Tekrar Oynamak İstiyor Musunuz");

        alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NormalGameActivity.this.finish();
            }
        });

        alert.show();
    }
}