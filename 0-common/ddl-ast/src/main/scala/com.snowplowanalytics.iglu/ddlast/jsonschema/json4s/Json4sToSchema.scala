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
package json4s

import org.json4s.JValue

object Json4sToSchema {
  implicit val allFormats =
    org.json4s.DefaultFormats +
    StringSerializers.MinLengthSerializer +
    StringSerializers.MaxLengthSerializer +
    StringSerializers.PatternSerializer +
    ObjectSerializers.PropertiesSerializer +
    ObjectSerializers.AdditionalPropertiesSerializer +
    ObjectSerializers.RequiredSerializer +
    ObjectSerializers.PatternPropertiesSerializer +
    CommonSerializers.TypeSerializer +
    CommonSerializers.EnumSerializer +
    NumberSerializers.MaximumSerializer +
    NumberSerializers.MinimumSerializer +
    NumberSerializers.MultipleOfSerializer +
    ArraySerializers.AdditionalPropertiesSerializer +
    ArraySerializers.MaxItemsSerializer +
    ArraySerializers.MinItemsSerializer +
    ArraySerializers.ItemsSerializer

  implicit object Json4sToSchema extends ToSchema[JValue] {
    def parse(json: JValue): Option[Schema] =
      json.extractOpt[Schema]
  }
}

