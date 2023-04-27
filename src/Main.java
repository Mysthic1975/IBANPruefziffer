import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Eingabeaufforderung für den Benutzer, eine IBAN einzugeben
            System.out.print("Bitte geben Sie eine IBAN ein: ");
            String iban = scanner.nextLine();
            // Überprüfung, ob die eingegebene IBAN mindestens 5 Zeichen lang ist
            if (iban.length() < 5) {
                // Wenn die IBAN zu kurz ist, wird eine Fehlermeldung ausgegeben und der Benutzer wird aufgefordert, eine gültige IBAN einzugeben
                System.out.println("Ungültige Eingabe. Bitte geben Sie eine gültige IBAN ein.");
                continue;
            }
            // Ausgabe der eingegebenen IBAN
            System.out.println("IBAN: " + iban);
            // Berechnung und Ausgabe der Prüfziffer der IBAN
            System.out.println("Prüfziffer: " + holePruefziffer(iban));
            // Berechnung und Ausgabe der BBAN (Basic Bank Account Number) der IBAN
            String bban = holeBBAN(iban);
            System.out.println("BBAN: " + bban);
            // Umformatierung der IBAN durch Verschiebung des Landcodes und der Prüfziffer an das Ende der BBAN
            String umformatierteIBAN = umformatiereIBAN(iban);
            StringBuilder numerischeIBAN = new StringBuilder();
            for (int i = 0; i < umformatierteIBAN.length(); i++) {
                numerischeIBAN.append(Character.getNumericValue(umformatierteIBAN.charAt(i)));
            }
            String ibanNummer = numerischeIBAN.toString();
            // Ausgabe der umformatierten IBAN
            System.out.println("Umformatierte IBAN: " + ibanNummer);
            // Berechnung und Ausgabe der Prüfsumme modulo 97 der umformatierten IBAN
            int pruefsummeModulo97 = berechnePruefsummeModulo97(ibanNummer);
            System.out.println("Prüfsumme Modulo 97: " + pruefsummeModulo97);
            // Überprüfung, ob die IBAN gültig ist
            boolean ergebnis = istGueltig(iban);
            // Ausgabe des Ergebnisses der Gültigkeitsprüfung
            System.out.println("Ist die IBAN korrekt? " + (ergebnis ? "Ja" : "Nein"));

            // Berechnung und Ausgabe der Bankleitzahl (BLZ) aus der IBAN
            System.out.println("BLZ: " + holeBLZ(iban));
            // Berechnung und Ausgabe der Kontonummer aus der IBAN
            System.out.println("Kontonummer: " + holeKontonummer(iban));
            // Eingabeaufforderung für den Benutzer, ob er eine weitere IBAN testen möchte
            System.out.print("Möchten Sie eine weitere IBAN testen? (j/n): ");
            String antwort = scanner.nextLine();
            // Wenn der Benutzer nicht "j" eingibt, wird die Schleife beendet
            if (!antwort.equalsIgnoreCase("j")) {
                break;
            }
        }
    }

    // Methode zur Überprüfung der Gültigkeit einer IBAN
    public static boolean istGueltig(String iban) {
        // Umformatierung der IBAN durch Verschiebung des Landcodes und der Prüfziffer an das Ende der BBAN
        String umformatierteIBAN = umformatiereIBAN(iban);
        StringBuilder numerischeIBAN = new StringBuilder();
        for (int i = 0; i < umformatierteIBAN.length(); i++) {
            numerischeIBAN.append(Character.getNumericValue(umformatierteIBAN.charAt(i)));
        }
        String ibanNummer = numerischeIBAN.toString();
        // Berechnung der Prüfsumme modulo 97 der umformatierten IBAN
        int pruefsummeModulo97 = berechnePruefsummeModulo97(ibanNummer);
        // Überprüfung, ob die Prüfsumme modulo 97 gleich 1 ist
        return pruefsummeModulo97 == 1;
    }

    // Methode zur Berechnung der Prüfsumme modulo 97 einer umformatierten IBAN
    public static int berechnePruefsummeModulo97(String ibanNummer) {
        int mod = 0;
        for (int i = 0; i < ibanNummer.length(); i++) {
            mod = (mod * 10 + Character.getNumericValue(ibanNummer.charAt(i))) % 97;
        }
        return mod;
    }

    // Methode zur Berechnung der Prüfziffer einer IBAN
    public static String holePruefziffer(String iban) {
        char[] chars = iban.toCharArray();
        return String.valueOf(chars[2]) + chars[3];
    }

    // Methode zur Berechnung der BBAN (Basic Bank Account Number) einer IBAN
    public static String holeBBAN(String iban) {
        char[] chars = iban.toCharArray();
        StringBuilder bban = new StringBuilder();
        for (int i = 4; i < chars.length; i++) {
            bban.append(chars[i]);
        }
        return bban.toString();
    }

    // Methode zur Umformatierung einer IBAN durch Verschiebung des Landcodes und der Prüfziffer an das Ende der BBAN
    public static String umformatiereIBAN(String iban) {
        char[] chars = iban.toCharArray();
        StringBuilder bban = new StringBuilder();
        for (int i = 4; i < chars.length; i++) {
            bban.append(chars[i]);
        }
        String landCode = String.valueOf(chars[0]) + chars[1];
        String pruefziffer = String.valueOf(chars[2]) + chars[3];
        return bban + landCode + pruefziffer;
    }

    // Methode zur Berechnung der Bankleitzahl (BLZ) aus einer IBAN
    public static String holeBLZ(String iban) {
        char[] chars = iban.toCharArray();
        StringBuilder blz = new StringBuilder();
        for (int i = 4; i < 12; i++) {
            blz.append(chars[i]);
        }
        return blz.toString();
    }

    // Methode zur Berechnung der Kontonummer aus einer IBAN
    public static String holeKontonummer(String iban) {
        char[] chars = iban.toCharArray();
        StringBuilder kontonummer = new StringBuilder();
        for (int i = 12; i < chars.length; i++) {
            kontonummer.append(chars[i]);
        }
        return kontonummer.toString();
    }
}
