package com.keiver.madara.common.utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * * 单币种货币类，处理货币算术、币种和取整。
 *
 * <p>
 * 货币类中封装了货币金额和币种。目前金额在内部是long类型表示，
 * 单位是所属币种的最小货币单位（对人民币是分）。
 *
 * <p>
 * 目前，货币实现了以下主要功能：<br>
 * <ul>
 *   <li>支持货币对象与double(float)/long(int)/String/BigDecimal之间相互转换。
 *   <li>货币类在运算中提供与JDK中的BigDecimal类似的运算接口，
 *       BigDecimal的运算接口支持任意指定精度的运算功能，能够支持各种
 *       可能的财务规则。
 *   <li>货币类在运算中也提供一组简单运算接口，使用这组运算接口，则在
 *       精度处理上使用缺省的处理规则。
 *   <li>推荐使用Money，不建议直接使用BigDecimal的原因之一在于，
 *       使用BigDecimal，同样金额和币种的货币使用BigDecimal存在多种可能
 *       的表示，例如：new BigDecimal("10.5")与new BigDecimal("10.50")
 *       不相等，因为scale不等。使得Money类，同样金额和币种的货币只有
 *       一种表示方式，new Money("10.5")和new Money("10.50")应该是相等的。
 *   <li>不推荐直接使用BigDecimal的另一原因在于， BigDecimal是Immutable，
 *       一旦创建就不可更改，对BigDecimal进行任意运算都会生成一个新的
 *       BigDecimal对象，因此对于大批量统计的性能不够满意。Money类是
 *       mutable的，对大批量统计提供较好的支持。
 *   <li>提供基本的格式化功能。
 *   <li>Money类中不包含与业务相关的统计功能和格式化功能。业务相关的功能
 *       建议使用utility类来实现。
 *   <li>Money类实现了Serializable接口，支持作为远程调用的参数和返回值。
 *   <li>Money类实现了equals和hashCode方法。
 * </ul>
 * 
 * @author prd-wam
 * @version $Id: Money.java, v 0.1 2018年2月5日 下午5:13:11 prd-wam Exp $
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class Money implements Serializable, Comparable {

    //Fields
    /**
     * 缺省的币种代码，为CNY（人民币）。
     */
    private static final String DEFAULT_CURRENCY_CODE = "CNY";

    /**
     * 缺省的取整模式，为BigDecimal.ROUND_HALF_EVEN
     * （四舍五入，当小数位0.5时，则取最近的偶数）
     */
    private static final int    DEFAULT_ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;

    /**
     * 一组可能的元/分换算比例。
     * 
     * 此处，“分”是指货币的最小单位，“元”是货币的最常用单位。
     * 不同的货币有不同的元/分换算比例，如人民币是100，而日元则为1。
     */
    private static final int[]  centFactors           = new int[] { 1, 10, 100, 1000 };

    /**
     * 金额，以分为单位。
     */
    private long                cent;

    /**
     * 币种。
     */
    private Currency            currency;

    /**
     * 缺省构造器。
     * 
     * 创建一个具有缺省金额(0)和缺省币种的货币对象。
     */
    public Money() {
        this(0);
    }

    /**
     * 构造器。
     * 
     * 创建一个具有指定金额amount和缺省币种的货币对象。
     * 如果金额不能转换为整数分，则使用四舍五入方式取整。
     * 
     * 注意：由于double类型运算中存在误差，使用四舍五入方式
     * 取整的结果并不确定。因此，应尽量避免使用double类型创建
     * 货币类型。
     * 例：
     * assertEquals(999, Math.round(9.995 * 100));
     * assertEquals(1000, Math.round(999.5));
     * money = new Money((9.995));
     * assertEquals(999, money.getCent());
     * money = new Money(10.005);
     * assertEquals(1001, money.getCent());
     * 
     * @param amount 金额，以元为单位。
     */
    public Money(double amount) {
        this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    /**
     * 构造器。
     * 
     * 创建一个具有指定金额和缺省币种的货币对象。
     * 如果金额不能转换为整数分，则使用四舍五入方式取整。
     * 
     * 注意：由于double类型运算中存在误差，使用四舍五入方式
     * 取整的结果并不确定。因此，应尽量避免使用double类型创建
     * 货币类型。
     * 
     * 例：
     * assertEquals(999, Math.round(9.995 * 100));
     * assertEquals(1000, Math.round(999.5));
     * money = new Money((9.995));
     * assertEquals(999, money.getCent());
     * money = new Money(10.005);
     * assertEquals(1001, money.getCent());
     * 
     * @param amount 金额，以元为单位。
     * @param currency 币种。
     */
    public Money(double amount, Currency currency) {
        this.currency = currency;
        this.cent = Math.round(amount * getCentFactor());
    }

    /**
     * 构造器。
     * 
     * 创建一个具有金额yuan元，cent分和缺省币种的货币对象。
     * 
     * @param yuan 金额元数。
     * @param cent 金额分数。
     */
    public Money(long yuan, int cent) {
        this(yuan, cent, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    /**
     * 构造器。
     * 
     * 创建一个具有金额yuan元，cent分和置顶币种的货币对象。
     * 
     * @param yuan 金额元数。
     * @param cent 金额分数。
     * @param currency 金额币种。
     */
    public Money(long yuan, int cent, Currency currency) {
        this.currency = currency;
        this.cent = (yuan * getCentFactor()) + (cent % getCentFactor());
    }

    /**
     * 构造器。
     * 
     * 创建一个具有金额amount元和缺省币种的货币对象。
     * 
     * @param amount 金额，以元为单位。
     */
    public Money(String amount) {
        this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    /**
     * 构造器。
     * 
     * 创建一个具有金额amount元和指定币种currency的货币对象。
     * 
     * @param amount 金额，以元为单位。
     * @param currency 币种。
     */
    public Money(String amount, Currency currency) {
        this(new BigDecimal(amount), currency);
    }

    /**
     * 构造器。
     * 
     * 创建一个具有金额amount和指定币种currency的货币对象。
     * 如果金额不能转换为整数分，则使用指定的取整模式roundingMode取整。
     * 
     * @param amount 金额，以元为单位。
     * @param currency 币种。
     * @param roundingMode 取整模式。
     */
    public Money(String amount, Currency currency, int roundingMode) {
        this(new BigDecimal(amount), currency, roundingMode);
    }

    /**
     * 构造器。
     * 
     * 创建一个具有金额amount和缺省币种的货币对象。
     * 如果金额不能转换为整数分，则使用缺省取整模式DEFAULT_CURRENCY_CODE取整。
     * 
     * @param amount 金额，以元为单位。
     */
    public Money(BigDecimal amount) {
        this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    /**
     * 构造器。
     * 
     * 创建一个具有金额amount和缺省币种的货币对象。
     * 如果金额不能转换为整数分，则使用指定的取整模式roundingMode取整。
     * 
     * @param amount 金额，以元为单位。
     * @param roundingMode 取整模式。
     */
    public Money(BigDecimal amount, int roundingMode) {
        this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE), roundingMode);
    }

    /**
     * 构造器。
     * 
     * 创建一个具有金额amount和指定币种的货币对象。
     * 如果金额不能转换为整数分，则使用缺省取整模式DEFAULT_CURRENCY_CODE取整。
     * 
     * @param amount 金额，以元为单位。
     * @param currency 币种。
     */
    public Money(BigDecimal amount, Currency currency) {
        this(amount, currency, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 构造器。
     * 
     * 创建一个具有金额amount、指定币种和指定取整模式的货币对象。
     * 如果金额不能转换为整数分，则使用指定的取整模式roundingMode取整。
     * 
     * @param amount 金额，以元为单位。
     * @param currency 币种。
     * @param roundingMode 取整模式。
     */
    public Money(BigDecimal amount, Currency currency, int roundingMode) {
        this.currency = currency;
        this.cent = rounding(amount.movePointRight(currency.getDefaultFractionDigits()), roundingMode);
    }

    //bean方法============================
    /**
     * 获取本币种的元/分换算比率。
     * 
     * @return 本货币币种的元/分换算比率。
     */
    public int getCentFactor() {
        return centFactors[currency.getDefaultFractionDigits()];
    }

    /**
     * centFactor的setter方法，用于Json反序列化
     * 
     * @param centFactor
     */
    @SuppressWarnings("unused")
    private void setCentFactor(int centFactor) {
    }

    /**
     * 获取本货币对象代表的金额数。
     * 
     * @return 金额数，以元为单位。
     */
    public BigDecimal getAmount() {
        return BigDecimal.valueOf(cent, currency.getDefaultFractionDigits());
    }

    /**
     * 设置本货币对象代表的金额数。
     * 
     * @param amount 金额数，以元为单位。
     */
    public void setAmount(BigDecimal amount) {
        if (amount != null) {
            cent = rounding(amount.movePointRight(2), BigDecimal.ROUND_HALF_EVEN);
        }
    }

    /**
     * 获取本货币对象代表的金额数。
     * 
     * @return 金额数，以分为单位。
     */
    public long getCent() {
        return cent;
    }

    /**
     * 获取本货币对象的币种类型。
     * 
     * @return 本货币对象的币种类型。
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * 设置本货币对象的币种码。
     * 
     * @param currencyCode 币种，如果为null，设置为默认币种。
     * @throws IllegalArgumentException 所设置的币种码非ISO 4217标准支持的币种
     * @see #DEFAULT_CURRENCY_CODE
     */
    public void setCurrencyCode(String currencyCode) {
        if (null != currencyCode) {
            this.currency = Currency.getInstance(currencyCode);
        } else {
            this.currency = Currency.getInstance(DEFAULT_CURRENCY_CODE);
        }
    }

    /**
     * 获取本货币对象的币种码代码。
     * 
     * @return 币种码。
     */
    public String getCurrencyCode() {
        if (null == currency) {
            return DEFAULT_CURRENCY_CODE;
        } else {
            return currency.getCurrencyCode();
        }
    }

    //基本对象方法===========================
    /**
     * 判断本货币对象与另一货币对象是否相等。
     * 
     * 本货币对象与另一对象相等的充要条件是：
     * 1.另一对象也属于货币对象类；
     * 2.金额相同；
     * 3.币种相同。
     * 
     * @param other 待比较的另一货币对象。
     * @return true表示相等，false表示不相等。
     * 
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object other) {
        return (other instanceof Money) && equals((Money) other);
    }

    /**
     * 判断本货币对象与另一货币对象是否相等。
     * 
     * 本货币对象与另一对象相等的充要条件是：
     * 1.金额相同；
     * 2.币种相同。
     * 
     * @param other 待比较的另一货币对象。
     * @return true表示相等，false表示不相等。
     */
    public boolean equals(Money other) {
        return currency.equals(other.currency) && (cent == other.cent);
    }

    /**
     * 计算本货币对象的杂凑值。
     * @return 本货币对象杂凑值。
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return (int) (cent ^ (cent >>> 32));
    }

    //格式化方法============================
    /**
     * 生成本对象的缺省字符串表示。
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return getAmount().toString();
    }

    //Comparable接口=======================
    /**
     * 对象比较。
     * 
     * 比较本对象与另一对象的大小。
     * 如果待比较对象的对象类型不是Money，则抛出java.lang.ClassCastException。
     * 如果待比较对象的两个对象币种不同，则抛出java.lang.IllegalArgumentException。
     * 如果本货币对象的金额少于待比较货币对象，则返回-1。
     * 如果本货币对象的金额等于待比较货币对象，则返回0。
     * 如果本货币对象的金额大于待比较货币对象，则返回1。
     * 
     * @param other 另一货币对象。
     * @return -1表示小于，0表示等于，1表示大于。
     * @exception ClassCastException 待比较货币对象不是Money。
     *            IllegalArgumentException 待比较货币对象与本货币对象的币种不相同。
     *            
     * @see Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(Object other) {
        return compareTo((Money) other);
    }

    /**
     * 货币比较。
     * 
     * 比较本货币对象与另一货币对象的大小。
     * 如果待比较对象的两个对象币种不同，则抛出java.lang.IllegalArgumentException。
     * 如果本货币对象的金额少于待比较货币对象，则返回-1。
     * 如果本货币对象的金额等于待比较货币对象，则返回0。
     * 如果本货币对象的金额大于待比较货币对象，则返回1。
     * 
     * @param other 另一货币对象。
     * @return -1表示小于，0表示等于，1表示大于。
     * @exception IllegalArgumentException 待比较货币对象与本货币对象的币种不相同。 
     */
    public int compareTo(Money other) {
        assertSameCurrencyAs(other);
        if (cent < other.cent) {
            return -1;
        } else if (cent == other.cent) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 货币比较。
     * 
     * 比较本货币对象是否大于另一货币对象。
     * 如果待比较对象的两个对象币种不同，则抛出java.lang.IllegalArgumentException。
     * 如果本货币对象的金额大于待比较货币对象，则返回true，否则返回false。
     * 
     * @param other 另一货币对象。
     * @return false表示不大于（小于等于），true表示大于。
     * @exception IllegalArgumentException 待比较货币对象与本货币对象的币种不相同。 
     */
    public boolean greaterThan(Money other) {
        return compareTo(other) > 0;
    }

    //货币算术==============================
    /**
     * 货币加法。
     * 
     * 如果两货币币种相同，则返回一个新的相同币种的货币对象，其金额为
     * 两货币对象金额之和，本货币对象的值不变。
     * 如果两货币对象币种不同，则抛出java.lang.IllegalArgumentException
     * 
     * @param other 作为加数的货币对象。
     * @exception IllegalArgumentException 如果本货币对象与另一货币对象币种不同。
     * @return 相加后的结果。
     */
    public Money add(Money other) {
        assertSameCurrencyAs(other);
        return newMoneyWithSameCurrency(cent + other.cent);
    }

    /**
     * 货币累加。
     * 
     * 如果两货币币种相同，则本货币对象的金额等于两货币对象金额之和，并返回本货币对象的引用。
     * 如果两货币对象币种不同，抛出java.lang.IllegalArgumentException.
     * 
     * @param other 作为加数的货币对象。
     * @exception IllegalArgumentException 如果本货币对象与另一货币对象币种不同。
     * @return 累加后的本货币对象。
     */
    public Money addTo(Money other) {
        assertSameCurrencyAs(other);
        this.cent += other.cent;
        return this;
    }

    /**
     * 货币减法。
     * 
     * 如果两货币币种相同，则返回一个新的相同币种的货币对象，其金额为
     * 本货币对象的金额减去参数货币对象的金额。本货币对象的值不变。
     * 如果两货币对象币种不同，则抛出java.lang.IllegalArgumentException
     * 
     * @param other 作为减数的货币对象。
     * @exception IllegalArgumentException 如果本货币对象与另一货币对象币种不同。
     * @return 相减后的结果。
     */
    public Money subtract(Money other) {
        assertSameCurrencyAs(other);
        return newMoneyWithSameCurrency(cent - other.cent);
    }

    /**
     * 货币累减。
     * 
     * 如果两货币币种相同，则本货币对象的金额等于两货币对象金额之差，并返回本货币对象的引用。
     * 如果两货币对象币种不同，抛出java.lang.IllegalArgumentException.
     * 
     * @param other 作为减数的货币对象。
     * @exception IllegalArgumentException 如果本货币对象与另一货币对象币种不同。
     * @return 累减后的本货币对象。
     */
    public Money subtractFrom(Money other) {
        assertSameCurrencyAs(other);
        this.cent -= other.cent;
        return this;
    }

    /**
     * 货币乘法。
     * 
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数。
     * 本货币对象的值不变。
     * 
     * @param val 乘数
     * @return 乘法后的结果。
     */
    public Money multiply(long val) {
        return newMoneyWithSameCurrency(cent * val);
    }

    /**
     * 货币累乘。
     * 
     * 本货币对象金额乘以乘数，并返回本货币对象。
     * 
     * @param val 乘数
     * @return 累乘后的本货币对象。
     */
    public Money multiplyBy(long val) {
        this.cent *= val;
        return this;
    }

    /**
     * 货币乘法。
     * 
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数。
     * 本货币对象的值不变。如果相乘后的金额不能转换为整数分，则四舍五入。
     * 
     * @param val 乘数
     * @return 乘法后的结果。
     */
    public Money multiply(double val) {
        return newMoneyWithSameCurrency(Math.round(cent * val));
    }

    /**
     * 货币累乘。
     * 
     * 本货币对象金额乘以乘数，并返回本货币对象。
     * 如果相乘后的金额不能转换为整数分，则四舍五入。
     * 
     * @param val 乘数
     * @return 累乘后的本货币对象。
     */
    public Money multiplyBy(double val) {
        this.cent = Math.round(this.cent * val);
        return this;
    }

    /**
     * 货币乘法。
     * 
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数。
     * 本货币对象的值不变。如果相乘后的金额不能转换为整数分，使用缺省的取整模式
     * DEFAULT_ROUNDING_MODE进行取整。
     * 
     * @param val 乘数
     * @return 乘法后的结果。
     */
    public Money multiply(BigDecimal val) {
        return multiply(val, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 货币累乘。
     * 
     * 本货币对象金额乘以乘数，并返回本货币对象。
     * 如果相乘后的金额不能转换为整数分，使用缺省的取整模式
     * DEFAULT_ROUNDING_MODE进行取整。
     * 
     * @param val 乘数
     * @return 累乘后的结果。
     */
    public Money multiplyBy(BigDecimal val) {
        return multiplyBy(val, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 货币乘法。
     * 
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数。
     * 本货币对象的值不变。如果相乘后的金额不能转换为整数分，使用指定的取整方式
     * roundingMode进行取整。
     * 
     * @param val 乘数
     * @param roundingMode 取整方式
     * @return 相乘后的结果。
     */
    public Money multiply(BigDecimal val, int roundingMode) {
        BigDecimal newCent = BigDecimal.valueOf(cent).multiply(val);
        return newMoneyWithSameCurrency(rounding(newCent, roundingMode));
    }

    /**
     * 货币累乘。
     * 
     * 本货币对象金额乘以乘数，并返回本货币对象。
     * 如果相乘后的金额不能转换为整数分，使用指定的取整模式
     * roundingMode进行取整。
     * 
     * @param val 乘数
     * @param roundingMode 取整方式
     * @return 相乘后的结果。
     */
    public Money multiplyBy(BigDecimal val, int roundingMode) {
        BigDecimal newCent = BigDecimal.valueOf(cent).multiply(val);
        this.cent = rounding(newCent, roundingMode);
        return this;
    }

    /**
     * 货币除法。
     * 
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额除以除数。
     * 本货币对象的值不变。如果相除后的金额不能转换为整数分，使用四舍五入方式取整。
     * 
     * @param val 除数
     * @return 相除后的结果。
     */
    public Money divide(double val) {
        return newMoneyWithSameCurrency(Math.round(cent / val));
    }

    /**
     * 货币累除。
     * 
     * 本货币对象金额除以除数，并返回本货币对象。
     * 如果相乘后的金额不能转换为整数分，使用四舍五入方式取整。
     * 
     * @param val 除数
     * @return 累除后的结果。
     */
    public Money divideBy(double val) {
        this.cent = Math.round(this.cent / val);
        return this;
    }

    /**
     * 货币除法。
     * 
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额除以除数。
     * 本货币对象的值不变。如果相除后的金额不能转换为整数分，使用缺省的取整方式
     * DEFAULT_ROUNDING_MODE进行取整。
     * 
     * @param val 除数
     * @return 相除后的结果。
     */
    public Money divide(BigDecimal val) {
        return divide(val, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 货币累除。
     * 
     * 本货币对象金额除以除数，并返回本货币对象。
     * 如果相乘后的金额不能转换为整数分，使用缺省的取整方式
     * DEFAULT_ROUNDING_MODE进行取整。
     * 
     * @param val 除数
     * @return 累除后的结果。
     */
    public Money divideBy(BigDecimal val) {
        return divideBy(val, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 货币除法。
     * 
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额除以除数。
     * 本货币对象的值不变。如果相除后的金额不能转换为整数分，使用指定的取整方式
     * roundingMode进行取整。
     * 
     * @param val 除数
     * @return 相除后的结果。
     */
    public Money divide(BigDecimal val, int roundingMode) {
        BigDecimal newCent = BigDecimal.valueOf(cent).divide(val, roundingMode);
        return newMoneyWithSameCurrency(newCent.longValue());
    }

    /**
     * 货币累除。
     * 
     * 本货币对象金额除以除数，并返回本货币对象。
     * 如果相乘后的金额不能转换为整数分，使用指定的取整方式
     * roundingMode进行取整。
     * 
     * @param val 除数
     * @return 累除后的结果。
     */
    public Money divideBy(BigDecimal val, int roundingMode) {
        BigDecimal newCent = BigDecimal.valueOf(cent).divide(val, roundingMode);
        this.cent = newCent.longValue();
        return this;
    }

    /**
     * 货币分配。
     * 
     * 将本货币对象尽可能平均分配成targets份。
     * 如果不能平均分配尽，则将零头放在开始的若干份中。
     * 分配运算能够确保不会丢失金额零头。
     * 
     * @param targets 待分配的份数。
     * @return 货币对象数组，数组长度与分配份数相同，数组元素
     *         从大到小排列，所有货币对象的金额最多只相差1分。
     */
    public Money[] allocate(int targets) {
        Money[] results = new Money[targets];

        Money lowResult = newMoneyWithSameCurrency(cent / targets);
        Money highResult = newMoneyWithSameCurrency(lowResult.cent + 1);

        int remainder = (int) cent % targets;

        for (int i = 0; i < remainder; i++) {
            results[i] = highResult;
        }

        for (int i = remainder; i < targets; i++) {
            results[i] = lowResult;
        }

        return results;
    }

    /**
     * 货币分配。
     * 
     * 将本货币对象按照规定的比例分配成若干份。
     * 分配所剩的零头从第一份开始顺序分配。
     * 分配运算确保不会丢失金额零头。
     * 
     * @param ratios 分配比例数组，每一个比例是一个长整型，代表相对于总数的相对数。
     * @return 货币对象数组，数组的长度与分配比例数组的长度相同。
     */
    public Money[] allocate(long[] ratios) {
        Money[] results = new Money[ratios.length];

        long total = 0;

        for (int i = 0; i < ratios.length; i++) {
            total += ratios[i];
        }

        long remainder = cent;

        for (int i = 0; i < results.length; i++) {
            results[i] = newMoneyWithSameCurrency((cent * ratios[i]) / total);
            remainder -= results[i].cent;
        }

        for (int i = 0; i < remainder; i++) {
            results[i].cent++;
        }

        return results;
    }

    //内部方法==============================
    /**
     * 断言本货币对象与另一货币对象是否具有相同的币种。
     * 
     * 如果本货币对象与另一货币对象具有相同的币种则方法返回。
     * 否则跑出运行时异常，IllegalArgumentException。
     * 
     * @param other 另一货币对象
     * @exception IllegalArgumentException 如果本货币 对象与另一货币对象币种不同。
     */
    protected void assertSameCurrencyAs(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Money math currency mismatch.");
        }
    }

    /**
     * 对BigDecimal型的值按指定取整方式取整
     * 
     * @param val 待取整的BigDecimal值。
     * @param roundingMode 取整模式。
     * @return 取整后的long型值。
     */
    protected long rounding(BigDecimal val, int roundingMode) {
        return val.setScale(0, roundingMode).longValue();
    }

    /**
     * 创建一个币种相同，具有指定金额的货币对象。
     * 
     * @param cent 金额，以分为单位。
     * @return 一个新建的币种相同的、具有指定金额的货币对象。
     */
    protected Money newMoneyWithSameCurrency(long cent) {
        Money money = new Money(0, currency);

        money.cent = cent;

        return money;
    }

    //调试方式==============================
    /**
     * 生成本对象内部变量的字符串表示，用于调试。
     * 
     * @return 本对象内部变量的字符串表示。
     */
    public String dump() {
        String lineSeparator = System.getProperty("line.separator");

        StringBuffer sb = new StringBuffer();

        sb.append("cent = ").append(cent).append(lineSeparator);
        sb.append("currency = ").append(currency);

        return sb.toString();
    }

    /**
     * 设置货币的分值。
     * 
     * @param l
     */
    public void setCent(long l) {
        cent = l;
    }

}
