import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Bitte geben Sie eine IBAN ein: ");
            String iban = scanner.nextLine();
            if (iban.length() < 5) {
                System.out.println("Ungültige Eingabe. Bitte geben Sie eine gültige IBAN ein.");
                continue;
            }
            System.out.println("IBAN: " + iban);
            System.out.println("Prüfziffer: " + holePruefziffer(iban));
            String bban = holeBBAN(iban);
            System.out.println("BBAN: " + bban);
            String umformatierteIBAN = umformatiereIBAN(iban);
            StringBuilder numerischeIBAN = new StringBuilder();
            for (int i = 0; i < umformatierteIBAN.length(); i++) {
                numerischeIBAN.append(Character.getNumericValue(umformatierteIBAN.charAt(i)));
            }
            String ibanNummer = numerischeIBAN.toString();
            System.out.println("Umformatierte IBAN: " + ibanNummer);
            int pruefsummeModulo97 = berechnePruefsummeModulo97(ibanNummer);
            System.out.println("Prüfsumme Modulo 97: " + pruefsummeModulo97);
            boolean ergebnis = istGueltig(iban);
            System.out.println("Ist die IBAN korrekt? " + (ergebnis ? "Ja" : "Nein"));

            // Hier werden die neuen Methoden verwendet
            System.out.println("BLZ: " + holeBLZ(iban));
            System.out.println("Kontonummer: " + holeKontonummer(iban));

            System.out.print("Möchten Sie eine weitere IBAN testen? (j/n): ");
            String antwort = scanner.nextLine();
            if (!antwort.equalsIgnoreCase("j")) {
                break;
            }
        }
    }

    public static boolean istGueltig(String iban) {
        String umformatierteIBAN = umformatiereIBAN(iban);
        StringBuilder numerischeIBAN = new StringBuilder();
        for (int i = 0; i < umformatierteIBAN.length(); i++) {
            numerischeIBAN.append(Character.getNumericValue(umformatierteIBAN.charAt(i)));
        }
        String ibanNummer = numerischeIBAN.toString();
        int pruefsummeModulo97 = berechnePruefsummeModulo97(ibanNummer);
        return pruefsummeModulo97 == 1;
    }

    public static int berechnePruefsummeModulo97(String ibanNummer) {
        int mod = 0;
        for (int i = 0; i < ibanNummer.length(); i++) {
            mod = (mod * 10 + Character.getNumericValue(ibanNummer.charAt(i))) % 97;
        }
        return mod;
    }

    public static String holePruefziffer(String iban) {
        char[] chars = iban.toCharArray();
        return String.valueOf(chars[2]) + chars[3];
    }

    public static String holeBBAN(String iban) {
        return iban.substring(4);
    }

    public static String umformatiereIBAN(String iban) {
        return iban.substring(4) + iban.charAt(0) + Character.getNumericValue(iban.charAt(1)) + Character.getNumericValue(iban.charAt(2)) + Character.getNumericValue(iban.charAt(3));
    }

   
    public static String holeBLZ(String iban) {
        return iban.substring(4, 12);
    }

    public static String holeKontonummer(String iban) {
        return iban.substring(12);
    }
}
