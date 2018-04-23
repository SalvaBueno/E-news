package salva.e_news.util;

import java.util.Calendar;

public class DateFormat {
    private String dia;
    private String mes;
    private String ano;

    public DateFormat(String date){
        setDia(date.substring(8));
        setMes(date.substring(5,7));
        setAno(date.substring(0,4));
    }

    public void formatDate(){
        String mes_texto="";
        switch (getMes()){
            case "1":
                mes_texto = "enero";
                break;
            case "2":
                mes_texto = "febrero";
                break;
            case "3":
                mes_texto = "marzo";
                break;
            case "4":
                mes_texto = "abril";
                break;
            case "5":
                mes_texto = "mayo";
                break;
            case "6":
                mes_texto = "junio";
                break;
            case "7":
                mes_texto = "julio";
                break;
            case "8":
                mes_texto = "agosto";
                break;
            case "9":
                mes_texto = "septiembre";
                break;
            case "10":
                mes_texto = "octubre";
                break;
            case "11":
                mes_texto = "noviembre";
                break;
            case "12":
                mes_texto = "diciembre";
                break;
        }
        if(mes_texto.length()>1)
            setMes(mes_texto);
    }

    public static int numeroSemanas(int anio) {
        Calendar c = Calendar.getInstance();
        c.set(anio, 0, 1);
        return c.getActualMaximum(Calendar.WEEK_OF_YEAR);
    }

    public String getDate(){
        return getDia() +"/"+ getMes() +"/"+ getAno();
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }
}
