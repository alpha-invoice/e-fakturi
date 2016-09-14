package bg.registryagency.utility;

import java.util.LinkedList;
import java.util.Queue;

import bg.registryagency.schemas.deedv2.fields.AddressType;

public class AddressTypeParser {

    private AddressType addressType;

    public AddressTypeParser(AddressType addressType) {
        this.addressType = addressType;
    }

    /**
     * Returns the street name and the street number as a string
     *
     * @return
     */
    public String getFullStreetName() {
        StringBuilder sb = new StringBuilder();
        if (isValidTagString(addressType.getStreet())) {
            sb.append(addressType.getStreet());
            if (isValidTagString(addressType.getStreetNumber())) {
                sb.append(" ").append(addressType.getStreetNumber());
            }
        }

        return sb.toString();
    }

    public String getHousingEstate() {
        StringBuilder sb = new StringBuilder();
        if (isValidTagString(addressType.getHousingEstate())) {
            sb.append("ж.к. ").append(addressType.getHousingEstate());
        }

        return sb.toString();
    }

    public String getBlock() {
        StringBuilder sb = new StringBuilder();
        if (isValidTagString(addressType.getBlock())) {
            sb.append("бл. ").append(addressType.getBlock());
        }

        return sb.toString();
    }

    public String getPostCode() {
        if (isValidTagString(addressType.getPostCode())) {
            return addressType.getPostCode();
        }

        return null;
    }

    public String getSettlement() {
        if (isValidTagString(addressType.getSettlement())) {
            return addressType.getSettlement();
        }

        return null;
    }

    public String getCountry() {
        if (isValidTagString(addressType.getCountry())) {
            return addressType.getCountry();
        }

        return null;
    }

    private boolean isValidTagString(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public String getFullAddress() {
        Queue<String> addressFields = new LinkedList<>();

        addressFields.add(getFullStreetName());
        addressFields.add(getHousingEstate());
        addressFields.add(getBlock());
        addressFields.add(getSettlement());
        addressFields.add(getCountry());
        addressFields.add(getPostCode());

        addressFields.removeIf(str -> !isValidTagString(str));
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

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

}
