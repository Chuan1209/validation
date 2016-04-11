package com.keruyun.gateway.validation.bean;


import com.google.gson.annotations.SerializedName;
import com.keruyun.gateway.validation.annotation.ValidateProperty;
import com.keruyun.gateway.validation.response.Pager;
import com.keruyun.gateway.validation.type.OperatorsType;
import com.keruyun.gateway.validation.type.RegexType;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * sql条件
 *
 * @author pul
 */
public class SqlCondition<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5112478410251596059L;

    @ValidateProperty(regexType = RegexType.OBJECT, clazz = Pager.class)
    @SerializedName("pager")
    private Pager<T> pager;

    public Pager<T> getPager() {
        return pager;
    }

    public void setPager(Pager<T> pager) {
        this.pager = pager;
    }

    @ValidateProperty(regexType = RegexType.ARRAY, nullable = true, clazz = KeyValue.class)
    @SerializedName("conditions")
    private List<KeyValue> conditions;// 条件集合


    private List<Object> params;

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public SqlCondition() {
    }

    /**
     * @param isCount-是否用于统计(true-是，false-否) @return String @throws
     */
    public String sqlCondition(boolean isCount) {
        StringBuffer sb = new StringBuffer(" 1=1 ");
        if (this.params == null) {
            this.params = new ArrayList<>();
        }
        if (this.conditions != null && this.conditions.size() > 0) {
            StringBuffer orderBy = new StringBuffer();
            for (KeyValue condition : conditions) {
                if (null == condition.getValue()) {
                    continue;
                } else if (condition.getValue() instanceof String) {
                    if (StringUtils.isBlank(condition.getValue().toString())) {
                        continue;
                    }
                }
                OperatorsType operator = OperatorsType.getType(condition.getOperator());
                if (operator == OperatorsType.ASC || operator == OperatorsType.DESC) {
                    orderBy.append(" ORDER BY ").append(condition.getKey()).append(" ").append(operator.getKey());
                } else {
                    switch (operator) {
                        case LIKE:
                            sb.append(" AND ").append(condition.getKey()).append(" ").append(operator.getKey()).append(" ? ");
                            params.add("%" + condition.getValue() + "%");
                            break;
                        case _LIKE:
                            sb.append(" AND ").append(condition.getKey()).append(" LIKE  ? ");
                            params.add("_" + condition.getValue() + "%");
                            break;
                        default:
                            sb.append(" AND ").append(condition.getKey()).append(operator.getKey()).append("? ");
                            params.add(condition.getValue());
                            break;
                    }
                }
            }
            sb.append(orderBy);
        }
        // 添加分页
        if (!isCount) {// 非统计用
            if (this.pager != null && pager.getStart() >= 0 && pager.getPageSize() > 0) {
                sb.append(" LIMIT ? , ? ");
                params.add(pager.getStart());
                params.add(pager.getPageSize());
            }
        }

        return sb.toString();
    }

    public List<KeyValue> getConditions() {
        return conditions;
    }

    public void setConditions(List<KeyValue> conditions) {
        this.conditions = conditions;
    }
}
