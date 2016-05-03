package me.excel.tools.extractor;

import me.excel.tools.FieldUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static me.excel.tools.FieldUtils.getFieldWithoutPrefix;

/**
 * Created by hanwen on 5/3/16.
 */
public class LocalDateTimeExtractor extends AbstractCellValueExtractor {

  private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateTimeExtractor.class);

  private String pattern;

  public LocalDateTimeExtractor(String matchField, String pattern) {
    super(matchField);
    this.pattern = pattern;
  }

  @Override
  public String getStringValue(Object data, String field) {

    String fieldWithoutPrefix;

    if (field.contains(FieldUtils.BUSINESS_KEY_PREFIX)) {
      fieldWithoutPrefix = getFieldWithoutPrefix(FieldUtils.getBusinessKeyField(field));
    } else {
      fieldWithoutPrefix = getFieldWithoutPrefix(field);
    }

    try {
      LocalDateTime value = (LocalDateTime) PropertyUtils.getProperty(data, fieldWithoutPrefix);

      return value.toString(pattern);

    } catch (NestedNullException e) {
      LOGGER.trace(e.getMessage());
      return "";
    } catch (Exception e) {
      LOGGER.error(ExceptionUtils.getStackTrace(e));
      throw new IllegalArgumentException(e);
    }
  }
}
