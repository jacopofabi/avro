/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.avro.mytests;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.avro.AvroRuntimeException;
import org.apache.avro.AvroTypeException;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData.Fixed;
import org.apache.avro.reflect.Union;
import org.apache.avro.specific.SpecificData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class SpecificDataCreateSchemaTest {

  private java.lang.reflect.Type classType;
  private Object result;
  Collection<String> array = new LinkedList<String>();
  Map<String, String> map = new HashMap<>();
  Map<Integer, Integer> mapInt = new HashMap<>();

  @Parameterized.Parameters
  public static Collection BufferedChannelParameters() throws Exception {
    return Arrays.asList(new Object[][] { 
      
      // Suite minimale
      {null, AvroTypeException.class},
      {getClassType("array"), Schema.Type.ARRAY},
      {getClassType("boolean"), Schema.Type.BOOLEAN},
      {getClassType("bytes"), Schema.Type.BYTES},
      {getClassType("double"), Schema.Type.DOUBLE},
      {getClassType("enum"), AvroRuntimeException.class},
      {getClassType("fixed"), AvroRuntimeException.class},
      {getClassType("float"), Schema.Type.FLOAT}, 
      {getClassType("int"), Schema.Type.INT},
      {getClassType("long"), Schema.Type.LONG},  
      {getClassType("map"), Schema.Type.MAP}, 
      {getClassType("void"), Schema.Type.NULL}, 
      {getClassType("record"), AvroRuntimeException.class},
      {getClassType("string"), Schema.Type.STRING},
      {getClassType("union"), AvroRuntimeException.class},
      {getClassType("other"), AvroRuntimeException.class},

      // Coverage
      {Integer.TYPE, Schema.Type.INT},
      {Long.TYPE, Schema.Type.LONG},
      {Float.TYPE, Schema.Type.FLOAT},
      {Double.TYPE, Schema.Type.DOUBLE},
      {Boolean.TYPE, Schema.Type.BOOLEAN},
      {Void.TYPE, Schema.Type.NULL},
      {getClassType("mapInt"), AvroTypeException.class}
      
    	});
  }

  public SpecificDataCreateSchemaTest(java.lang.reflect.Type classType, Object result) {
    this.classType = classType;
    this.result = result;
  }

  
  /**
   * 
   * Return the Java Type from a String. 
   *   
   *    */
  public static java.lang.reflect.Type getClassType(String classTypeString) throws NoSuchFieldException, SecurityException{
    
    java.lang.reflect.Type classType = null;
    Field attributeField = null;
    switch (classTypeString) {
    case "int":
      classType = Integer.class;
      break;
      
    case "boolean":
        classType = Boolean.class;
        break;

    case "void":
      classType = Void.class;
      break;

    case "long":
      classType = Long.class;
      break;

    case "float":
      classType = Float.class;
      break;

    case "double":
      classType = Double.class;
      break;

    case "bytes":
      classType = ByteBuffer.class;
      break;

    case "string":
      classType = String.class;
      break;
      
    case "enum":
      classType = Enum.class;
      break;

    case "fixed":
      classType = Fixed.class;
      break;  
      
    case "record":
      classType = Record.class;
      break;
      
    case "union":
        classType = Union.class;
        break;
      
    case "map":
      attributeField = SpecificDataCreateSchemaTest.class.getDeclaredField("map");
      classType = (ParameterizedType) attributeField.getGenericType();
      break;

    case "mapInt":
    attributeField = SpecificDataCreateSchemaTest.class.getDeclaredField("mapInt");
    classType = (ParameterizedType) attributeField.getGenericType();
    break;

    case "array":
      attributeField = SpecificDataCreateSchemaTest.class.getDeclaredField("array");
      classType = (ParameterizedType) attributeField.getGenericType();
      break;

    default:
      classType = Exception.class;
    }

    return classType;
  }

  @Test
  public void testGetSchema() throws IOException, NoSuchFieldException, SecurityException { 
    
    try {
    	Assert.assertEquals(result, SpecificData.get().getSchema(classType).getType());
    } catch (Exception e) {
    	Assert.assertEquals(result, e.getClass());
    }

  }

}