package utility;

import java.util.LinkedList;
import java.util.Queue;

import bg.registryagency.LegalFormsConverter;
import bg.registryagency.schemas.deedv2.DeedType;
import bg.registryagency.schemas.deedv2.fields.AddressType;

public class DeedTypeParser {

    private DeedType deedType;

    public DeedTypeParser(DeedType deedType) {
        this.deedType = deedType;
    }

    public Long getEik() {
        return Long.parseLong(deedType.getUIC());
    }

    public String getName() {
        return deedType.getCompanyName() + " " + LegalFormsConverter.getLegalFormInBulgarian(deedType.getLegalForm());
    }

    public String getMol() {
        try {
            return deedType.getSubDeed().get(0).getManagers().get(0).getManager().get(0).getPerson().getName();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public String getAddress() {
        AddressType addressType;
        try {
            addressType = deedType.getSubDeed().get(0).getSeat().get(0).getAddress();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        Queue<String> addressFields = new LinkedList<>();

        addressFields.add(addressType.getStreet());
        addressFields.add(addressType.getStreetNumber());
        addressFields.add(addressType.getSettlement());
        addressFields.add(addressType.getCountry());
        addressFields.add(addressType.getPostCode());

        StringBuilder sb = new StringBuilder();
        String property = null;
       
        while (!addressFields.isEmpty()) {
            property = addressFields.poll();
            if (property != null && !property.trim().isEmpty()) {
                sb.append(property);
                if (addressFields.size() != 0) {
                    sb.append(", ");
                }
            }
        }

        return sb.toString();
    }

}
