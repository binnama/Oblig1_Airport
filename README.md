# Oblig1_Airport
Les data/parameterverdier for simuleringen fra bruker

+    Initier begge køene til å være tomme
    
    For hver tidssteg i simuleringen
    
+        Trekk et tilfeldig antall nye fly som kommer for å lande
    
        For hvert nytt fly som kommer for å lande
            Hvis landingskøen er full
                Avvis det nye flyet (henvis til annen flyplass)
            ellers
                Sett det nye flyet sist i landingskøen
          
+        Trekk et tilfeldig antall nye fly som kommer for å ta av
    
        For hvert nytt fly som kommer for å ta av
            Hvis avgangskøen er full
                Avvis det nye flyet (avgang må prøves senere)
            ellers
                Sett det nye flyet sist i avgangskøen
    
        Hvis landingskøen ikke er tom
            Ta ut første fly i landingskøen og la det få lande
        ellers hvis avgangskøen ikke er tom
            Ta ut første fly i avgangskøen og la det få ta av
        ellers
            Flyplassen er tom for fly
    
    Skriv til slutt ut resultater som gj.snittlig ventetid etc.