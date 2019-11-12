/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.getstarted.objects;

import java.util.List;
import java.util.Objects;

// [START example]

/**
 * To parameter(use generic type) List & curor
 * @param <K>
 */
public class Result<K> {

  public String cursor;
  public List<K> result;

  /**
   * Constructor
   * @param result list
   * @param cursor string
   */
  public Result(List<K> result, String cursor) {
    this.result = result;
    this.cursor = cursor;
  }
  /**
   * Constructor
   * @param result list
   *  cursor null
   */
  public Result(List<K> result) {
    this.result = result;
    this.cursor = null;
  }

  /**
   * Customized toString() with instance variables
   * @return
   */
  @Override
  public String toString() {
    return "Result{" +
            "cursor='" + cursor + '\'' +
            ", result=" + result +
            '}';
  }

  /**
   * Customized equals() with instance variables
   * @return
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Result<?> result1 = (Result<?>) o;
    return Objects.equals(cursor, result1.cursor) &&
            Objects.equals(result, result1.result);
  }
}
// [END example]
