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
package com.snowplowanalytics.iglu.ddlast
package jsonschema

// Shadow Java Enum
import java.lang.{ Enum => _}

// This library
import ArrayProperties._
import StringProperties._
import ObjectProperties._
import NumberProperties._
import CommonProperties._

case class Schema(
  // integer and number
  multipleOf:           Option[MultipleOf]           = None,
  minimum:              Option[Minimum]              = None,
  maximum:              Option[Maximum]              = None,

  // string
  maxLength:            Option[MaxLength]            = None,
  minLength:            Option[MinLength]            = None,
  pattern:              Option[Pattern]              = None,
  format:               Option[Format]               = None,

  // array
  items:                Option[Items]                = None,
  additionalItems:      Option[AdditionalItems]      = None,
  minItems:             Option[MinItems]             = None,
  maxItems:             Option[MaxItems]             = None,

  // object
  properties:           Option[Properties]           = None,
  additionalProperties: Option[AdditionalProperties] = None,
  required:             Option[Required]             = None,
  patternProperties:    Option[PatternProperties]    = None,

  // common
  `type`:               Option[Type]                 = None,
  enum:                 Option[Enum]                 = None
) {
  override def toString: String = {
    val props = List(multipleOf, minimum, maximum, maxLength, minLength,
      pattern, format, items, additionalItems, minItems, maxItems, properties,
      additionalProperties, required, patternProperties, `type`, enum)
      .flatten.mkString(", ")

    s"Schema($props)"
  }
}

object Schema {
  def parse[J: ToSchema](json: J): Option[Schema] =
    implicitly[ToSchema[J]].parse(json)
}
