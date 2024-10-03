package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static long differenceInMinutes(Date dataInicio, Date dataFim) {
        long diferencaEmMilissegundos = dataFim.getTime() - dataInicio.getTime();

        return Math.abs(diferencaEmMilissegundos / (1000 * 60));
    }

    public static boolean isPresent(long minutes) {
        return minutes >= 135;
    }

    public static Date stringToDate(String dataHora) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return formato.parse(dataHora);
    }
}
