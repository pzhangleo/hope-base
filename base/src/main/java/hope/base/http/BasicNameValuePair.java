//package hope.base.http;
//
//import java.io.Serializable;
//
///**
// * Created by zhangpeng on 15/12/23.
// */
//public class BasicNameValuePair implements Cloneable, Serializable {
//
//    private static final long serialVersionUID = -6437800749411518984L;
//
//    private final String name;
//    private final String value;
//
//    /**
//     * Default Constructor taking a name and a value. The value may be null.
//     *
//     * @param name The name.
//     * @param value The value.
//     */
//    public BasicNameValuePair(final String name, final String value) {
//        super();
//        this.name = name;
//        this.value = value;
//    }
//
//    public String getName() {
//        return this.name;
//    }
//
//    public String getValue() {
//        return this.value;
//    }
//
//    @Override
//    public String toString() {
//        // don't call complex default formatting for a simple toString
//
//        if (this.value == null) {
//            return name;
//        }
//        final int len = this.name.length() + 1 + this.value.length();
//        final StringBuilder buffer = new StringBuilder(len);
//        buffer.append(this.name);
//        buffer.append("=");
//        buffer.append(this.value);
//        return buffer.toString();
//    }
//
//    @Override
//    public boolean equals(final Object object) {
//        if (this == object) {
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = LangUtils.HASH_SEED;
//        hash = LangUtils.hashCode(hash, this.name);
//        hash = LangUtils.hashCode(hash, this.value);
//        return hash;
//    }
//
//    @Override
//    public Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }
//
//}
