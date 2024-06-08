package org.unify4j.model.c;

import org.unify4j.common.Object4j;
import org.unify4j.common.String4j;
import org.unify4j.common.Vi4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Media type used in representations and preferences. The {@link #getName()}
 * method returns a full String representation of the media type including the
 * parameters.
 *
 * @see <a href="http://en.wikipedia.org/wiki/MIME">MIME types on Wikipedia</a>
 */
@SuppressWarnings({"SpellCheckingInspection"})
public class MediaType extends Metadata {
    /**
     * Illegal ASCII characters as defined in RFC 1521.<br>
     * Keep the underscore for the ordering
     */
    protected static final String TYPE_SPECIALS = "()<>@,;:/[]?=\\\"";

    /**
     * The known media types registered with {@link #register(String, String)},
     * retrievable using {@link #valueOf(String)}.<br>
     * Keep the underscore for the ordering.
     */
    protected static volatile Map<String, MediaType> _types = null;

    public static final MediaType ALL = register("*/*", "All media");

    public static final MediaType APPLICATION_ALL = register("application/*", "All application documents");

    public static final MediaType APPLICATION_ALL_XML = register("application/*+xml", "All application/*+xml documents");

    public static final MediaType APPLICATION_ATOM = register("application/atom+xml", "Atom document");

    public static final MediaType APPLICATION_ATOMPUB_CATEGORY = register("application/atomcat+xml", "Atom category document");

    public static final MediaType APPLICATION_ATOMPUB_SERVICE = register("application/atomsvc+xml", "Atom service document");

    public static final MediaType APPLICATION_CAB = register("application/vnd.ms-cab-compressed", "Microsoft Cabinet archive");

    public static final MediaType APPLICATION_COMPRESS = register("application/x-compress", "Compressed file");

    public static final MediaType APPLICATION_EXCEL = register("application/vnd.ms-excel", "Microsoft Excel document");

    public static final MediaType APPLICATION_FLASH = register("application/x-shockwave-flash", "Shockwave Flash object");

    public static final MediaType APPLICATION_GNU_TAR = register("application/x-gtar", "GNU Tar archive");

    public static final MediaType APPLICATION_GNU_ZIP = register("application/x-gzip", "GNU Zip archive");

    public static final MediaType APPLICATION_HTTP_COOKIES = register("application/x-http-cookies", "HTTP cookies");

    public static final MediaType APPLICATION_JAVA = register("application/java", "Java class");

    public static final MediaType APPLICATION_JAVA_ARCHIVE = register("application/java-archive", "Java archive");

    public static final MediaType APPLICATION_JAVA_OBJECT = register("application/x-java-serialized-object", "Java serialized object");

    public static final MediaType APPLICATION_JAVA_OBJECT_GWT = register("application/x-java-serialized-object+gwt", "Java serialized object (using GWT-RPC encoder)");

    public static final MediaType APPLICATION_JAVA_OBJECT_XML = register("application/x-java-serialized-object+xml", "Java serialized object (using JavaBeans XML encoder)");

    public static final MediaType APPLICATION_JAVASCRIPT = register("application/x-javascript", "Javascript document");

    public static final MediaType APPLICATION_JNLP = register("application/x-java-jnlp-file", "JNLP");

    public static final MediaType APPLICATION_JSON = register("application/json", "JavaScript Object Notation document");

    public static final MediaType APPLICATION_KML = register("application/vnd.google-earth.kml+xml", "Google Earth/Maps KML document");

    public static final MediaType APPLICATION_KMZ = register("application/vnd.google-earth.kmz", "Google Earth/Maps KMZ document");

    public static final MediaType APPLICATION_LATEX = register("application/x-latex", "LaTeX");

    public static final MediaType APPLICATION_MAC_BINHEX40 = register("application/mac-binhex40", "Mac binhex40");

    public static final MediaType APPLICATION_MATHML = register("application/mathml+xml", "MathML XML document");

    public static final MediaType APPLICATION_MSOFFICE_DOCM = register("application/vnd.ms-word.document.macroEnabled.12", "Office Word 2007 macro-enabled document");

    public static final MediaType APPLICATION_MSOFFICE_DOCX = register("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "Microsoft Office Word 2007 document");

    public static final MediaType APPLICATION_MSOFFICE_DOTM = register("application/vnd.ms-word.template.macroEnabled.12", "Office Word 2007 macro-enabled document template");

    public static final MediaType APPLICATION_MSOFFICE_DOTX = register("application/vnd.openxmlformats-officedocument.wordprocessingml.template", "Office Word 2007 template");

    public static final MediaType APPLICATION_MSOFFICE_ONETOC = register("application/onenote", "Microsoft Office OneNote 2007 TOC");

    public static final MediaType APPLICATION_MSOFFICE_ONETOC2 = register("application/onenote", "Office OneNote 2007 TOC");

    public static final MediaType APPLICATION_MSOFFICE_POTM = register("application/vnd.ms-powerpoint.template.macroEnabled.12", "Office PowerPoint 2007 macro-enabled presentation template");

    public static final MediaType APPLICATION_MSOFFICE_POTX = register("application/vnd.openxmlformats-officedocument.presentationml.template", "Office PowerPoint 2007 template");

    public static final MediaType APPLICATION_MSOFFICE_PPAM = register("application/vnd.ms-powerpoint.addin.macroEnabled.12", "Office PowerPoint 2007 add-in");

    public static final MediaType APPLICATION_MSOFFICE_PPSM = register("application/vnd.ms-powerpoint.slideshow.macroEnabled.12", "Office PowerPoint 2007 macro-enabled slide show");

    public static final MediaType APPLICATION_MSOFFICE_PPSX = register("application/vnd.openxmlformats-officedocument.presentationml.slideshow", "Office PowerPoint 2007 slide show");

    public static final MediaType APPLICATION_MSOFFICE_PPTM = register("application/vnd.ms-powerpoint.presentation.macroEnabled.12", "Office PowerPoint 2007 macro-enabled presentation");

    public static final MediaType APPLICATION_MSOFFICE_PPTX = register("application/vnd.openxmlformats-officedocument.presentationml.presentation", "Microsoft Office PowerPoint 2007 presentation");

    public static final MediaType APPLICATION_MSOFFICE_SLDM = register("application/vnd.ms-powerpoint.slide.macroEnabled.12", "Office PowerPoint 2007 macro-enabled slide");

    public static final MediaType APPLICATION_MSOFFICE_SLDX = register("application/vnd.openxmlformats-officedocument.presentationml.slide", "Office PowerPoint 2007 slide");

    public static final MediaType APPLICATION_MSOFFICE_XLAM = register("application/vnd.ms-excel.addin.macroEnabled.12", "Office Excel 2007 add-in");

    public static final MediaType APPLICATION_MSOFFICE_XLSB = register("application/vnd.ms-excel.sheet.binary.macroEnabled.12", "Office Excel 2007 binary workbook");

    public static final MediaType APPLICATION_MSOFFICE_XLSM = register("application/vnd.ms-excel.sheet.macroEnabled.12", "Office Excel 2007 macro-enabled workbook");

    public static final MediaType APPLICATION_MSOFFICE_XLSX = register("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Microsoft Office Excel 2007 workbook");

    public static final MediaType APPLICATION_MSOFFICE_XLTM = register("application/vnd.ms-excel.template.macroEnabled.12", "Office Excel 2007 macro-enabled workbook template");

    public static final MediaType APPLICATION_MSOFFICE_XLTX = register("application/vnd.openxmlformats-officedocument.spreadsheetml.template", "Office Excel 2007 template");

    public static final MediaType APPLICATION_OCTET_STREAM = register("application/octet-stream", "Raw octet stream");

    public static final MediaType APPLICATION_OPENOFFICE_ODB = register("application/vnd.oasis.opendocument.database", "OpenDocument Database");

    public static final MediaType APPLICATION_OPENOFFICE_ODC = register("application/vnd.oasis.opendocument.chart", "OpenDocument Chart");

    public static final MediaType APPLICATION_OPENOFFICE_ODF = register("application/vnd.oasis.opendocument.formula", "OpenDocument Formula");

    public static final MediaType APPLICATION_OPENOFFICE_ODG = register("application/vnd.oasis.opendocument.graphics", "OpenDocument Drawing");

    public static final MediaType APPLICATION_OPENOFFICE_ODI = register("application/vnd.oasis.opendocument.image", "OpenDocument Image ");

    public static final MediaType APPLICATION_OPENOFFICE_ODM = register("application/vnd.oasis.opendocument.text-master", "OpenDocument Master Document");

    public static final MediaType APPLICATION_OPENOFFICE_ODP = register("application/vnd.oasis.opendocument.presentation", "OpenDocument Presentation ");

    public static final MediaType APPLICATION_OPENOFFICE_ODS = register("application/vnd.oasis.opendocument.spreadsheet", "OpenDocument Spreadsheet");

    public static final MediaType APPLICATION_OPENOFFICE_ODT = register("application/vnd.oasis.opendocument.text ", "OpenDocument Text");

    public static final MediaType APPLICATION_OPENOFFICE_OTG = register("application/vnd.oasis.opendocument.graphics-template", "OpenDocument Drawing Template");

    public static final MediaType APPLICATION_OPENOFFICE_OTH = register("application/vnd.oasis.opendocument.text-web", "HTML Document Template");

    public static final MediaType APPLICATION_OPENOFFICE_OTP = register("application/vnd.oasis.opendocument.presentation-template", "OpenDocument Presentation Template");

    public static final MediaType APPLICATION_OPENOFFICE_OTS = register("application/vnd.oasis.opendocument.spreadsheet-template", "OpenDocument Spreadsheet Template");

    public static final MediaType APPLICATION_OPENOFFICE_OTT = register("application/vnd.oasis.opendocument.text-template", "OpenDocument Text Template");

    public static final MediaType APPLICATION_OPENOFFICE_OXT = register("application/vnd.openofficeorg.extension", "OpenOffice.org extension");

    public static final MediaType APPLICATION_PDF = register("application/pdf", "Adobe PDF document");

    public static final MediaType APPLICATION_POSTSCRIPT = register("application/postscript", "Postscript document");

    public static final MediaType APPLICATION_POWERPOINT = register("application/vnd.ms-powerpoint", "Microsoft Powerpoint document");

    public static final MediaType APPLICATION_PROJECT = register("application/vnd.ms-project", "Microsoft Project document");

    public static final MediaType APPLICATION_RDF_TRIG = register("application/x-trig", "Plain text serialized Resource Description Framework document");

    public static final MediaType APPLICATION_RDF_TRIX = register("application/trix", "Simple XML serialized Resource Description Framework document");

    public static final MediaType APPLICATION_RDF_TURTLE = register("application/x-turtle", "Plain text serialized Resource Description Framework document");

    public static final MediaType APPLICATION_RDF_XML = register("application/rdf+xml", "Normalized XML serialized Resource Description Framework document");

    public static final MediaType APPLICATION_RELAXNG_COMPACT = register("application/relax-ng-compact-syntax", "Relax NG Schema document, Compact syntax");

    public static final MediaType APPLICATION_RELAXNG_XML = register("application/x-relax-ng+xml", "Relax NG Schema document, XML syntax");

    public static final MediaType APPLICATION_RSS = register("application/rss+xml", "Really Simple Syndication document");

    public static final MediaType APPLICATION_RTF = register("application/rtf", "Rich Text Format document");

    public static final MediaType APPLICATION_SPARQL_RESULTS_JSON = register("application/sparql-results+json", "SPARQL Query Results JSON document");

    public static final MediaType APPLICATION_SPARQL_RESULTS_XML = register("application/sparql-results+xml", "SPARQL Query Results XML document");

    public static final MediaType APPLICATION_SPSS_SAV = register("application/x-spss-sav", "SPSS Data");

    public static final MediaType APPLICATION_SPSS_SPS = register("application/x-spss-sps", "SPSS Script Syntax");

    public static final MediaType APPLICATION_STATA_STA = register("application/x-stata", "Stata data file");

    public static final MediaType APPLICATION_STUFFIT = register("application/x-stuffit", "Stuffit archive");

    public static final MediaType APPLICATION_TAR = register("application/x-tar", "Tar archive");

    public static final MediaType APPLICATION_TEX = register("application/x-tex", "Tex file");

    public static final MediaType APPLICATION_TROFF_MAN = register("application/x-troff-man", "LaTeX");

    public static final MediaType APPLICATION_VOICEXML = register("application/voicexml+xml", "VoiceXML");

    public static final MediaType APPLICATION_W3C_SCHEMA = register("application/x-xsd+xml", "W3C XML Schema document");

    public static final MediaType APPLICATION_W3C_XSLT = register("application/xslt+xml", "W3C XSLT Stylesheet");

    public static final MediaType APPLICATION_WADL = register("application/vnd.sun.wadl+xml", "Web Application Description Language document");

    public static final MediaType APPLICATION_WORD = register("application/msword", "Microsoft Word document");

    public static final MediaType APPLICATION_WWW_FORM = register("application/x-www-form-urlencoded", "Web form (URL encoded)");

    public static final MediaType APPLICATION_XHTML = register("application/xhtml+xml", "XHTML document");

    public static final MediaType APPLICATION_XMI_XML = register("application/xmi+xml", "XMI document");

    public static final MediaType APPLICATION_XML = register("application/xml", "XML document");

    public static final MediaType APPLICATION_XML_DTD = register("application/xml-dtd", "XML DTD");

    public static final MediaType APPLICATION_XUL = register("application/vnd.mozilla.xul+xml", "XUL document");

    public static final MediaType APPLICATION_ZIP = register("application/zip", "Zip archive");

    public static final MediaType AUDIO_ALL = register("audio/*", "All audios");

    public static final MediaType AUDIO_BASIC = register("audio/basic", "AU audio");

    public static final MediaType AUDIO_MIDI = register("audio/midi", "MIDI audio");

    public static final MediaType AUDIO_MPEG = register("audio/mpeg", "MPEG audio (MP3)");

    public static final MediaType AUDIO_REAL = register("audio/x-pn-realaudio", "Real audio");

    public static final MediaType AUDIO_WAV = register("audio/x-wav", "Waveform audio");

    public static final MediaType IMAGE_ALL = register("image/*", "All images");

    public static final MediaType IMAGE_BMP = register("image/bmp", "Windows bitmap");

    public static final MediaType IMAGE_GIF = register("image/gif", "GIF image");

    public static final MediaType IMAGE_ICON = register("image/x-icon", "Windows icon (Favicon)");

    public static final MediaType IMAGE_JPEG = register("image/jpeg", "JPEG image");

    public static final MediaType IMAGE_PNG = register("image/png", "PNG image");

    public static final MediaType IMAGE_SVG = register("image/svg+xml", "Scalable Vector Graphics");

    public static final MediaType IMAGE_TIFF = register("image/tiff", "TIFF image");

    public static final MediaType MESSAGE_ALL = register("message/*", "All messages");

    public static final MediaType MODEL_ALL = register("model/*", "All models");

    public static final MediaType MODEL_VRML = register("model/vrml", "VRML");

    public static final MediaType MULTIPART_ALL = register("multipart/*", "All multipart data");

    public static final MediaType MULTIPART_FORM_DATA = register("multipart/form-data", "Multipart form data");

    public static final MediaType TEXT_ALL = register("text/*", "All texts");

    public static final MediaType TEXT_CALENDAR = register("text/calendar", "iCalendar event");

    public static final MediaType TEXT_CSS = register("text/css", "CSS stylesheet");

    public static final MediaType TEXT_CSV = register("text/csv", "Comma-separated Values");

    public static final MediaType TEXT_DAT = register("text/x-fixed-field", "Fixed-width Values");

    public static final MediaType TEXT_HTML = register("text/html", "HTML document");

    public static final MediaType TEXT_J2ME_APP_DESCRIPTOR = register("text/vnd.sun.j2me.app-descriptor", "J2ME Application Descriptor");

    public static final MediaType TEXT_JAVASCRIPT = register("text/javascript", "Javascript document");

    public static final MediaType TEXT_PLAIN = register("text/plain", "Plain text");

    public static final MediaType TEXT_RDF_N3 = register("text/n3", "N3 serialized Resource Description Framework document");

    public static final MediaType TEXT_RDF_NTRIPLES = register("text/n-triples", "N-Triples serialized Resource Description Framework document");

    public static final MediaType TEXT_TSV = register("text/tab-separated-values", "Tab-separated Values");

    public static final MediaType TEXT_URI_LIST = register("text/uri-list", "List of URIs");

    public static final MediaType TEXT_VCARD = register("text/x-vcard", "vCard");

    public static final MediaType TEXT_XML = register("text/xml", "XML text");

    public static final MediaType VIDEO_ALL = register("video/*", "All videos");

    public static final MediaType VIDEO_AVI = register("video/x-msvideo", "AVI video");

    public static final MediaType VIDEO_MP4 = register("video/mp4", "MPEG-4 video");

    public static final MediaType VIDEO_MPEG = register("video/mpeg", "MPEG video");

    public static final MediaType VIDEO_QUICKTIME = register("video/quicktime", "Quicktime video");

    public static final MediaType VIDEO_WMV = register("video/x-ms-wmv", "Windows movie");

    /**
     * Constructor.
     *
     * @param name The name.
     */
    public MediaType(String name) {
        super(name, "Media type or range of media types");
    }


    /**
     * Constructor.
     *
     * @param name        The name.
     * @param description The description.
     */
    public MediaType(String name, String description) {
        super(name, description);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Returns the first of the most specific media type of the given array of
     * {@link MediaType}s.
     * <p>
     * Examples:
     * <ul>
     * <li>"text/plain" is more specific than "text/*" or "image/*"</li>
     * <li>"text/html" is same specific as "application/pdf" or "image/jpg"</li>
     * <li>"text/*" is same specific than "application/*" or "image/*"</li>
     * <li>"*<!---->/*" is the most unspecific MediaType</li>
     * </ul>
     *
     * @param mediaTypes An array of media types.
     * @return The most concrete MediaType.
     * @throws IllegalArgumentException If the array is null or empty.
     */
    public static MediaType getMostSpecific(MediaType... mediaTypes) throws IllegalArgumentException {
        Vi4j.throwIfTrue(Object4j.isEmpty(mediaTypes), "You must give at least one MediaType");
        if (mediaTypes.length == 1) {
            return mediaTypes[0];
        }
        MediaType most = mediaTypes[0];
        for (int i = 1; i < mediaTypes.length; i++) {
            MediaType mediaType = mediaTypes[i];
            if (mediaType != null) {
                if (mediaType.getMainType().equals("*")) {
                    continue;
                }
                if (most.getMainType().equals("*")) {
                    most = mediaType;
                    continue;
                }
                if (most.getSubType().contains("*")) {
                    most = mediaType;
                }
            }
        }
        return most;
    }

    /**
     * Returns the known media types map.
     *
     * @return the known media types map.
     */
    public static Map<String, MediaType> getTypes() {
        if (_types == null) {
            _types = new HashMap<>();
        }
        return _types;
    }

    /**
     * Register a media type as a known type that can later be retrieved using
     * {@link #valueOf(String)}. If the type already exists, the existing type
     * is returned, otherwise a new instance is created.
     *
     * @param name        The name.
     * @param description The description.
     * @return The registered media type
     */
    public static synchronized MediaType register(String name, String description) {
        if (!getTypes().containsKey(name)) {
            final MediaType type = new MediaType(name, description);
            getTypes().put(name, type);
        }
        return getTypes().get(name);
    }

    /**
     * Returns the media type associated to a name. If an existing constant
     * exists then it is returned, otherwise a new instance is created.
     *
     * @param name The name.
     * @return The associated media type.
     */
    public static MediaType valueOf(String name) {
        MediaType result = null;
        if (String4j.isNotEmpty(name)) {
            result = getTypes().get(name);
            if (result == null) {
                result = new MediaType(name);
            }
        }
        return result;
    }

    /**
     * Indicates if a given media type is included in the current one. The test
     * is true if both types are equal or if the given media type is within the
     * range of the current one. For example, ALL includes all media types.
     * Parameters are ignored for this comparison. A null media type is
     * considered as included into the current one.
     * <p>
     * Examples:
     * <ul>
     * <li>TEXT_ALL.includes(TEXT_PLAIN) -> true</li>
     * <li>TEXT_PLAIN.includes(TEXT_ALL) -> false</li>
     * </ul>
     *
     * @param included The media type to test for inclusion.
     * @return True if the given media type is included in the current one.
     * @see #isCompatible(Metadata)
     */
    @SuppressWarnings({"PatternVariableCanBeUsed"})
    public boolean includes(Metadata included) {
        boolean result = equals(ALL) || (included == null) || equals(included);

        if (!result && (included instanceof MediaType)) {
            MediaType type = (MediaType) included;
            if (getMainType().equals(type.getMainType())) {
                if (getSubType().equals(type.getSubType())) {
                    result = true;
                } else if (getSubType().equals("*")) {
                    result = true;
                } else if (getSubType().startsWith("*+") && type.getSubType().endsWith(getSubType().substring(2))) {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * Returns the main type.
     *
     * @return The main type.
     */
    public String getMainType() {
        String result = null;
        if (getName() != null) {
            int index = getName().indexOf('/');
            if (index == -1) {
                index = getName().indexOf(';');
            }
            if (index == -1) {
                result = getName();
            } else {
                result = getName().substring(0, index);
            }
        }
        return result;
    }

    /**
     * @return The subtype.
     */
    public String getSubType() {
        String result = null;
        if (getName() != null) {
            final int slash = getName().indexOf('/');
            if (slash == -1) {
                result = "*";
            } else {
                final int separator = getName().indexOf(';');
                if (separator == -1) {
                    result = getName().substring(slash + 1);
                } else {
                    result = getName().substring(slash + 1, separator);
                }
            }
        }
        return result;
    }
}
