package vip.ifmm.navicatweb.entity;

/**
 * @author: MackyHuang
 * @eamil: 973151766@qq.com
 * @createTime: 2019/3/21 15:17
 */
public class TableStructure {

    private String columnName;

    private String typeName;

    private String columnSize;

    private String decimalDigits;

    private String nullable;

    private String remark;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(String columnSize) {
        this.columnSize = columnSize;
    }

    public String getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(String decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TableStructure{" +
                "columnName='" + columnName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", columnSize='" + columnSize + '\'' +
                ", decimalDigits='" + decimalDigits + '\'' +
                ", nullable='" + nullable + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
