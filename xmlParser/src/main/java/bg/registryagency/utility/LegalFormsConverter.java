package bg.registryagency.utility;

import java.util.HashMap;
import java.util.Map;

public class LegalFormsConverter {

    private static final Map<String, String> bulgarianLegalForms;

    static {
        HashMap<String, String> map = new HashMap<>();
        map.put("AD", "АД");
        map.put("EOOD", "ЕООД");
        map.put("ET", "ЕТ");
        map.put("OOD", "ООД");
        map.put("SD", "СД");
        map.put("EAD", "ЕАД");
        map.put("KD", "КД");
        map.put("KDA", "КДА");
        bulgarianLegalForms = map;
    }

    public static String getLegalFormInBulgarian(String legalForm) {

        if (bulgarianLegalForms.containsKey(legalForm)) {
            return bulgarianLegalForms.get(legalForm);
        }
        return legalForm;
    }
}
