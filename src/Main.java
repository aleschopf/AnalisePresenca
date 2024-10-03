import util.FileUtil;


public class Main {
    public static void main(String[] args) {
        String pathToCSV = "src/resources/data/registro_alunos_multiplas_entradas.csv";
        String exportFile = "src/resources/data/presenca_agrupada.csv";
        FileUtil.processFile(pathToCSV, exportFile);
    }
}
