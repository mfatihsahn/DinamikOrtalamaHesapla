package com.example.ortalama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.yeni_ders_layout.view.*
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    private val DERSLER = arrayOf("Matematik", "Türkçe", "Fizik", "Edebiyat", "Algoritma", "Tarih")
    private var tumDerslerinBilgileri: ArrayList<Dersler> = ArrayList(5)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, DERSLER)
        etDersAd.setAdapter(adapter)
        if (rootLayout.childCount == 0) {
            btnOrtalamaHesapla.visibility = View.INVISIBLE
        } else btnOrtalamaHesapla.visibility = View.VISIBLE





        btnDersSec.setOnClickListener {

            if (!etDersAd.text.isNullOrEmpty()) {
                var inflater = LayoutInflater.from(this)


                var yeniDersView = inflater.inflate(R.layout.yeni_ders_layout, null)


                yeniDersView.etYeniDersAd.setAdapter(adapter)


                var dersAdi = etDersAd.text.toString()
                var dersKredi = spnDersKredi.selectedItem.toString()
                var dersHarf = spnHarfNotu.selectedItem.toString()


                yeniDersView.etYeniDersAd.setText(dersAdi)
                yeniDersView.spnYeniDersKredi.setSelection(
                    spinnerDegerinIndexiniBul(
                        spnDersKredi,
                        dersKredi
                    )
                )
                yeniDersView.spnYeniHarfNotu.setSelection(
                    spinnerDegerinIndexiniBul(
                        spnHarfNotu,
                        dersHarf
                    )
                )

                //sil butonuna silme görevi atandı
                yeniDersView.btnYeniDersSil.setOnClickListener {


                    rootLayout.removeView(yeniDersView)

                    //eğer dersler listesi boş ise veya silme sonrası listede ders kalmamıssa hesapla butonu saklanır, değilse görünür yapılır
                    if (rootLayout.childCount == 0) {
                        btnOrtalamaHesapla.visibility = View.INVISIBLE
                    } else btnOrtalamaHesapla.visibility = View.VISIBLE
                }



                rootLayout.addView(yeniDersView)

                //yeni bir ders alanı eklenirse hesapla butonu görünür yapıldı
                btnOrtalamaHesapla.visibility = View.VISIBLE


                sifirla()

            }
            //Eğer ders adı girilmeden ekle butonu tıklanırsa uyarı verme
            else {
// Toast mesajı
                FancyToast.makeText(
                    this,
                    "Ders Adını Giriniz !",
                    FancyToast.LENGTH_LONG,
                    FancyToast.ERROR,
                    true
                ).show();
            }
        }
    }
            fun sifirla() {
                etDersAd.setText("")
                spnDersKredi.setSelection(0)
                spnHarfNotu.setSelection(0)
            }
            private fun spinnerDegerinIndexiniBul(spnDersKredi: Spinner?, dersKredi: String): Int {

             var index = 0
            if (spnDersKredi != null) {
                for (i in 0..spnDersKredi.count) {

                    if (spnDersKredi?.getItemAtPosition(i).toString().equals(dersKredi)) {

                        index = i
                        break
                    }
                }
            }

                return index

                }

                fun ortalamaHesapla(view: View) {

                    var toplamNot: Double = 0.0
                    var toplamKredi = 0.0

                    for (i in 0..rootLayout.childCount - 1) {

                        var tekSatir = rootLayout.getChildAt(i)

                        var geciciDers = Dersler(
                            tekSatir.etYeniDersAd.text.toString(),
                            ((tekSatir.spnYeniDersKredi.selectedItemPosition) + 1).toString(),
                            tekSatir.spnYeniHarfNotu.selectedItem.toString()
                        )

                        tumDerslerinBilgileri.add(geciciDers)
                    }

                    for (oankiDers in tumDerslerinBilgileri) {

                        toplamNot += harfinotacevir(oankiDers.dersHarfNot) * (oankiDers.dersKredi.toDouble())

                        toplamKredi += oankiDers.dersKredi.toDouble()

                    }


//Toast Mesajı
                    FancyToast.makeText(
                        this,
                        "ORTALAMA : " + (toplamNot / toplamKredi),
                        FancyToast.LENGTH_LONG,
                        FancyToast.WARNING,
                        true
                    ).show();

                    tumDerslerinBilgileri.clear()

                }


                fun harfinotacevir (gelenNotHarfDegeri: String): Double {

                    var deger = 0.0

                    when (gelenNotHarfDegeri) {

                        "AA" -> deger = 4.0
                        "BA" -> deger = 3.5
                        "BB" -> deger = 3.0
                        "CB" -> deger = 2.5
                        "CC" -> deger = 2.0
                        "DC" -> deger = 1.5
                        "DD" -> deger = 1.0
                        "FF" -> deger = 0.0

                    }
                    return deger


                }
            }



