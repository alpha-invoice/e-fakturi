package bg.registryagency.utility;

import java.util.HashMap;
import java.util.Map;

import bg.registryagency.schemas.deedv2.DeedType;

public class LegalFormUtils {

    private static final Map<String, String> bulgarianLegalForms;
    private static final Map<String, ParseMolMethod> parseMolMap;

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
        map.put("IAD", "АДСИЦ");
        map.put("K", "K");
        bulgarianLegalForms = map;

        HashMap<String, ParseMolMethod> parser = new HashMap<>();

        parser.put("AD", (deedType) -> getRepresentative(deedType));
        parser.put("IAD", (deedType) -> getRepresentative(deedType));
        parser.put("EAD", (deedType) -> getRepresentative(deedType));
        parser.put("OOD", (deedType) -> getManager(deedType));
        parser.put("EOOD", (deedType) -> getManager(deedType));
        parser.put("SD", (deedType) -> getAssignedManager(deedType));
        parser.put("ET", (deedType) -> getPhysicalTrader(deedType));
        parser.put("K", (deedType) -> getChairman(deedType));
        parseMolMap = parser;
    }

    /**
     * Returns legalForm in Bulgarian translation.
     *
     * @param legalForm the legal form of the company.
     * @return
     */
    public static String getLegalFormInBulgarian(String legalForm) {
        if (bulgarianLegalForms.containsKey(legalForm)) {
            return bulgarianLegalForms.get(legalForm);
        }
        return legalForm;
    }

    public static String getMol(DeedType deedType) throws Exception {
        return parseMolMap.get(deedType.getLegalForm()).parseMolFromDeed(deedType);
    }

    private static String getManager(DeedType deedType) throws IndexOutOfBoundsException {
        return deedType.getSubDeed().get(0)
                .getManagers().get(0)
                .getManager().get(0)
                .getPerson().getName();
    }

    private static String getAssignedManager(DeedType deedType) throws IndexOutOfBoundsException {
        return deedType.getSubDeed().get(0)
                .getAssignedManagers().get(0)
                .getAssignedManager().get(0)
                .getSubject().getName();
    }

    private static String getRepresentative(DeedType deedType) throws IndexOutOfBoundsException {
        return deedType.getSubDeed().get(0)
                .getRepresentatives().get(0)
                .getRepresentative().get(0)
                .getPerson().getName();
    }

    private static String getPhysicalTrader(DeedType deedType) throws IndexOutOfBoundsException {
        return deedType.getSubDeed().get(0)
                .getPhysicalPersonTrader().get(0)
                .getPerson().getName();
    }

    private static String getChairman(DeedType deedType) throws IndexOutOfBoundsException {
        return deedType.getSubDeed().get(0)
                .getChairMan().get(0)
                .getPerson().getName();
    }

}
