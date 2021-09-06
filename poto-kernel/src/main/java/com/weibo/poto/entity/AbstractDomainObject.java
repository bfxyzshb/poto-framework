package com.weibo.poto.entity;


/**
 * @ClassName AbstractDomainObject
 * @Description TODO
 * @Author hebiao1
 * @Date 2021/8/31 10:59 上午
 * @Version 1.0
 */
public abstract class AbstractDomainObject<ID extends Identifier> implements DomainObject<ID> {

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        AbstractDomainObject<?> that = (AbstractDomainObject<?>) obj;
        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
        return hashCode;
    }
}
