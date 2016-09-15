package bg.registryagency.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LegalFormUtils {

    private static final Map<String, String> bulgarianLegalForms;
    private static final List<String> representedCompaniesByRepresentative;
    private static final List<String> representedCompaniesByManager;
    private static final List<String> assignedManagers;
    private static final List<String> trustee;

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
        bulgarianLegalForms = map;

        ArrayList<String> representativesList = new ArrayList<>();
        representativesList.add("AD");
        representedCompaniesByRepresentative = representativesList;

        ArrayList<String> managersList = new ArrayList<>();
        managersList.add("OOD");
        managersList.add("EOOD");
        representedCompaniesByManager = managersList;

        ArrayList<String> assignee = new ArrayList<>();
        assignee.add("SD");
        assignedManagers = assignee;

        ArrayList<String> trusteeForEAD = new ArrayList<>();
        trusteeForEAD.add("EAD");
        trustee = trusteeForEAD;
    }

    /**
     * Returns legalForm in Bulgarian translation.
     * 
     * @param legalForm
     *            the legal form of the company.
     * @return
     */
    public static String getLegalFormInBulgarian(String legalForm) {
        if (bulgarianLegalForms.containsKey(legalForm)) {
            return bulgarianLegalForms.get(legalForm);
        }
        return legalForm;
    }

    /**
     * Returns whether this company type is represented by Representatives.
     * 
     * @param legalForm
     *            The legal form of the company
     * @return true if the company is represented by Representatives.
     */
    public static boolean isCompanyMolRepresentative(String legalForm) {
        return representedCompaniesByRepresentative.contains(legalForm);
    }

    /**
     * Returns whether this company type is represented by Manager.
     * 
     * @param legalForm
     *            The legal form of the company
     * @return true if the company is represented by Manager.
     */
    public static boolean isCompanyMolManager(String legalForm) {
        return representedCompaniesByManager.contains(legalForm);
    }

    /**
     * Returns whether this company type is represented by Trustee.
     * 
     * @param legalForm
     *            The legal form of the company
     * @return true if the company is represented by Trustee.
     */
    public static boolean isCompanyMolTrustee(String legalForm) {
        return trustee.contains(legalForm);
    }

    /**
     * Returns whether this company type is represented by AssignedManager.
     * 
     * @param legalForm
     *            The legal form of the company
     * @return true if the company is represented by AssignedManager.
     */
    public static boolean isCompanyMolAssignedManager(String legalForm) {
        return assignedManagers.contains(legalForm);
    }

}
