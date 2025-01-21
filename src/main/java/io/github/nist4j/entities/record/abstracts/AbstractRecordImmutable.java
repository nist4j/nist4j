/*
 * Copyright (C) 2025 Sopra Steria.
 *
 * Licenced under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.nist4j.entities.record.abstracts;

import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.field.DataImage;
import io.github.nist4j.entities.field.DataText;
import io.github.nist4j.entities.field.impl.DataTextImmutableImpl;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import io.github.nist4j.exceptions.Nist4jException;
import java.util.*;
import java.util.Map.Entry;
import lombok.*;

@EqualsAndHashCode
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.NONE)
public abstract class AbstractRecordImmutable {

  protected final Integer recordId;
  protected final String recordName;
  protected final Map<Integer, Data> fields;

  public AbstractRecordImmutable(
      Integer recordId, String recordName, NistRecordBuilder nistRecordBuilder) {
    this.recordId = recordId;
    this.recordName = recordName;
    this.fields = unmodifiableMapOfCopies(nistRecordBuilder.getFields());

    if (!Objects.equals(nistRecordBuilder.getRecordId(), recordId)) {
      throw new Nist4jException(
          "Miss configuration exception the builder does not match the Record type Id");
    }
  }

  protected Map<Integer, Data> unmodifiableMapOfCopies(Map<Integer, Data> fields) {
    Map<Integer, Data> newCloneFields = new TreeMap<>();
    for (Entry<Integer, Data> entryField : fields.entrySet()) {
      newCloneFields.put(entryField.getKey(), entryField.getValue().deepCopy());
    }
    return Collections.unmodifiableMap(newCloneFields);
  }

  public List<Data> getAllFields() {
    return new ArrayList<>(fields.values());
  }

  protected abstract Set<IFieldTypeEnum> getIFieldTypeEnumValues();

  public String toString() {
    return this.getClass().getName()
        + "(id="
        + this.getRecordId()
        + ", name="
        + this.getRecordName()
        + ", fields="
        + this.getFields()
        + ")";
  }

  public Optional<IFieldTypeEnum> findFieldEnumById(Integer id) {
    return getIFieldTypeEnumValues().stream().filter(f -> f.getId() == id).findFirst();
  }

  public Optional<String> getFieldText(@NonNull IFieldTypeEnum field) {
    return getFieldText(field.getId());
  }

  public Optional<String> getFieldText(@NonNull Integer id) {
    Optional<Data> oFieldData = ofNullable(fields.get(id));
    if (!oFieldData.isPresent()) {
      return empty();
    } else if (oFieldData.get() instanceof DataTextImmutableImpl) {
      DataTextImmutableImpl dataTextImmutableImpl = (DataTextImmutableImpl) oFieldData.get();
      return ofNullable(dataTextImmutableImpl.getData());
    } else {
      throw new InvalidFormatNist4jException(format("Field %s isn't in text format", id));
    }
  }

  public Optional<byte[]> getFieldImage(@NonNull IFieldTypeEnum field) {
    return getFieldImage(field.getId());
  }

  public Optional<byte[]> getFieldImage(@NonNull Integer id) {

    Optional<Data> oFieldData = ofNullable(fields.get(id));
    if (!oFieldData.isPresent()) {
      return empty();
    } else if (oFieldData.get() instanceof DataImage) {
      DataImage dataImage = (DataImage) oFieldData.get();
      return ofNullable(dataImage.getData());
    } else {
      throw new InvalidFormatNist4jException(format("Field %s isn't in image format", id));
    }
  }

  public Optional<Integer> getFieldLength(@NonNull IFieldTypeEnum field) {
    return getFieldLength(field.getId());
  }

  public Optional<Integer> getFieldLength(@NonNull Integer id) {
    return ofNullable(fields.get(id)).map(Data::getLength);
  }

  public Optional<Data> getFieldData(@NonNull IFieldTypeEnum field) {
    return getFieldData(field.getId());
  }

  public Optional<Data> getFieldData(@NonNull Integer id) {
    return ofNullable(fields.get(id));
  }

  public Optional<Integer> getFieldAsInt(@NonNull IFieldTypeEnum field) {
    return getFieldAsInt(field.getId());
  }

  public Optional<Integer> getFieldAsInt(@NonNull Integer id) {
    Optional<Data> oFieldData = getFieldData(id);
    if (!oFieldData.isPresent()) {
      return empty();
    } else if (oFieldData.get() instanceof DataText) {
      DataText dataText = (DataText) oFieldData.get();
      return Optional.of(Integer.parseInt(dataText.getData()));
    } else {
      throw new InvalidFormatNist4jException(format("Field %s isn't in text format", id));
    }
  }
}
