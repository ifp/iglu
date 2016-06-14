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

import org.json4s.JsonAST.{JArray, JString}
import org.json4s._

import jsonschema.CommonProperties._

object CommonSerializers {

  private def stringToType(json: JValue): Option[Type] = json match {
    case JString("null")    => Some(Null)
    case JString("boolean") => Some(Boolean)
    case JString("string")  => Some(String)
    case JString("integer") => Some(Integer)
    case JString("number")  => Some(Number)
    case JString("array")   => Some(Array)
    case JString("object")  => Some(Object)
    case _                  => None
  }

  object TypeSerializer extends CustomSerializer[Type](_ => (
    {
      case JArray(ts) =>
        val types = ts.map(stringToType)
        val union = if (types.exists(_.isEmpty)) None else Some(types.map(_.get))
        union match {
          case Some(List(t)) => t
          case Some(u)       => Product(u)
          case None          => throw new MappingException(ts + " is not valid list of types")
        }
      case str @ JString(t) =>
        stringToType(str) match {
          case Some(singleType) => singleType
          case None             => throw new MappingException(str + " is not valid list of types")
        }
      case x =>
        throw new MappingException(x + " is not valid list of types")
    },

    {
      case t: Type => t.asJson
    }
    ))


  object EnumSerializer extends CustomSerializer[Enum](_ => (
    {
      case JArray(values) => Enum(values)
      case x => throw new MappingException(x + " isn't valid enum")
    },

    {
      case Enum(values) => JArray(values)
    }
    ))
}
