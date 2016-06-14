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
package jsonschema.json4s

// json4s
import org.json4s._

// This library
import jsonschema.StringProperties._

object StringSerializers {

  implicit object MinLengthSerializer extends CustomSerializer[MinLength](_ => (
    {
      case JInt(value) if value >= 0 => MinLength(value)
      case x => throw new MappingException(x + " isn't minLength")
    },

    {
      case MinLength(value) => JInt(value)
    }
    ))


  implicit object MaxLengthSerializer extends CustomSerializer[MaxLength](_ => (
    {
      case JInt(value) if value >= 0 => MaxLength(value)
      case x => throw new MappingException(x + " isn't maxLength")
    },

    {
      case MaxLength(value) => JInt(value)
    }
    ))

  implicit object PatternSerializer extends CustomSerializer[Pattern](x => (
    {
      case JString(value) => Pattern(value)
      case x => throw new MappingException(x + " isn't valid regex")
    },

    {
      case Pattern(value) => JString(value)
    }
    ))
}
