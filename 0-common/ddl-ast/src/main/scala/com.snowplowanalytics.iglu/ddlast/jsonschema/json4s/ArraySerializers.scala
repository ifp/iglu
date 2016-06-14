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

// this library
import jsonschema.Schema
import jsonschema.ArrayProperties._

object ArraySerializers {

  implicit val formats = DefaultFormats
  import Json4sToSchema._

  object ItemsSerializer extends CustomSerializer[Items](_ => (
    {
      case schema: JObject =>
        Schema.parse(schema.asInstanceOf[JValue]) match {
          case Some(s) => ListItems(s)
          case None    => throw new MappingException(schema + " isn't Schema")
        }
      case tuple: JArray =>
        val schemas: List[Option[Schema]] = tuple.arr.map(Schema.parse(_))
        if (schemas.forall(_.isDefined)) TupleItems(schemas.map(_.get))
        else throw new MappingException(tuple + " need to be array of Schemas")
      case x => throw new MappingException(x + " isn't valid items")
    },

    {
      case ListItems(schema) => Extraction.decompose(schema)
      case TupleItems(schemas) => JArray(schemas.map(Extraction.decompose(_)))
    }
    ))

  object AdditionalPropertiesSerializer extends CustomSerializer[AdditionalItems](_ => (
    {
      case JBool(bool) => AdditionalItems(bool)
      case x => throw new MappingException(x + " isn't bool")
    },

    {
      case AdditionalItems(value) => JBool(value)
    }
    ))

  object MinItemsSerializer extends CustomSerializer[MinItems](_ => (
    {
      case JInt(value) => MinItems(value)
      case x => throw new MappingException(x + " isn't minLength")
    },

    {
      case MinItems(value) => JInt(value)
    }
    ))


  object MaxItemsSerializer extends CustomSerializer[MaxItems](_ => (
    {
      case JInt(value) => MaxItems(value)
      case x => throw new MappingException(x + " isn't maxItems")
    },

    {
      case MaxItems(value) => JInt(value)
    }
    ))
}
