/*
 * Copyright (c) 2012-2016 Snowplow Analytics Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package com.snowplowanalytics.iglu.ddlast.jsonschema

// json4s
import org.json4s._

object CommonProperties {

  /**
   * Type representing value for `type` key in JSON Schema
   */
  sealed trait Type {
    def asJson: JValue
  }

  case object Null extends Type {
    def asJson = JString("null")
  }

  case object Boolean extends Type {
    def asJson = JString("boolean")
  }

  case object String extends Type {
    def asJson = JString("string")
  }

  case object Integer extends Type {
    def asJson = JString("integer")
  }

  case object Number extends Type {
    def asJson = JString("number")
  }

  case object Array extends Type {
    def asJson = JString("array")
  }

  case object Object extends Type {
    def asJson = JString("object")
  }

  case class Product(value: List[Type]) extends Type {
    def asJson = JArray(value.map(_.asJson))
  }

  case class Enum(value: List[JValue])
}



