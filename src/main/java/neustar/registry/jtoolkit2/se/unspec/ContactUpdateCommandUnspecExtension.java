package neustar.registry.jtoolkit2.se.unspec;

import org.w3c.dom.Element;

import neustar.registry.jtoolkit2.se.Command;
import neustar.registry.jtoolkit2.se.CommandExtension;
import neustar.registry.jtoolkit2.se.ExtendedObjectType;
import neustar.registry.jtoolkit2.xml.XMLWriter;

/**
 * <p>Extension for the EPP Contact Update command, indicating the contact can be used as an EXTContact.  This will
 * be acomplished by the use of the EPP unspec extension with a special extContact value.</p>
 *
 * <p>Use this to command to mark a contact as an EXTContact which will cause NYC specific address validation
 * to be performed. </p>
 *
 * @see neustar.registry.jtoolkit2.se.DomainRenewCommand
 */
public class ContactUpdateCommandUnspecExtension implements CommandExtension {

    private Boolean extContact;
    private String nexusCategory;
    private String appPurpose;


    /**
     * Constructor for the unspec extension used by contact update command.
     *
     * <p>
     *     After have such a object created, the caller shall specify data which they would like
     *     to be present in the extension, by calling {@link #setExtContact(Boolean)},
     *     {@link #setNexusCategory(String)}, and/or {@link #setAppPurpose(String)}.
     * </p>
     * <p>
     *     For example, the code below would generate unspec content as "nexusCategory=Foo appPurpose=Bar"
     * </p>
     * <pre>{@code
     *     ContactUpdateCommandUnspecExtension ext = new ContactUpdateCommandUnspecExtension();
     *     ext.setNexusCategory("Foo");
     *     ext.setAppPurpose("Bar");
     * }</pre>
     */
    public ContactUpdateCommandUnspecExtension() {
        // intentionally do nothing
    }

    /**
     * Deprecated constructor for the unspec extension used by contact create command.
     *
     * @param extContact true would output "extContact=Y", while false would output "extContact=N"
     * @param nexusCategory a string literal for "nexusCategory"
     * @deprecated As of release 3.7.9, replaced by {@link #ContactUpdateCommandUnspecExtension()}
     */
    @Deprecated
    public ContactUpdateCommandUnspecExtension(Boolean extContact, String nexusCategory) {
        if (extContact != null) {
            this.extContact = extContact.booleanValue();
        }
        this.nexusCategory = nexusCategory;
    }

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element unspecElement = xmlWriter.appendChild(extensionElement, "extension",
                ExtendedObjectType.UNSPEC.getURI());

        StringBuilder unspecText =  new StringBuilder();
        if (extContact != null) {
            unspecText.append(" extContact=" + (extContact.booleanValue() ? "Y" : "N"));
        }
        if (nexusCategory != null) {
            unspecText.append(" nexusCategory=" + nexusCategory);
        }
        if (appPurpose != null) {
            unspecText.append(" appPurpose=" + appPurpose);
        }

        xmlWriter.appendChild(unspecElement, "unspec",
                ExtendedObjectType.UNSPEC.getURI()).setTextContent(unspecText.toString());

    }

    /**
     * A boolean flag to indicate the desired output for "ExtContact" in the unspec extension.
     *
     * @param extContact true would output "extContact=Y", while false would output "extContact=N"
     * @since version 3.7.9
     */
    public void setExtContact(Boolean extContact) {
        this.extContact = extContact;
    }

    /**
     * A string literal for "nexusCategory" in the unspec extension.
     * @param nexusCategory string literal
     * @since version 3.7.9
     */
    public void setNexusCategory(String nexusCategory) {
        this.nexusCategory = nexusCategory;
    }

    /**
     * A string literal for "appPurpose" in the unspec extension.
     * @param appPurpose string literal
     * @since version 3.7.9
     */
    public void setAppPurpose(String appPurpose) {
        this.appPurpose = appPurpose;
    }
}
