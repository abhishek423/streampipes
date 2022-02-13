/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.pe.processor.${packageName};

import ${package}.config.ConfigKeys;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.streampipes.client.StreamPipesClient;
import org.apache.streampipes.container.config.ConfigExtractor;
import org.apache.streampipes.model.graph.DataProcessorInvocation;
import org.apache.streampipes.model.runtime.Event;
import org.apache.streampipes.sdk.extractor.ProcessingElementParameterExtractor;
import org.apache.streampipes.wrapper.flink.FlinkDataProcessorRuntime;
import org.apache.streampipes.wrapper.flink.FlinkDeploymentConfig;
import org.myorga.config.ConfigKeys;
import org.myorga.pe.processor.example.MyFlinkProcessor;
import org.myorga.pe.processor.example.MyFlinkProcessorParameters;
import org.myorga.pe.processor.example.MyFlinkProcessorProgram;
import org.apache.streampipes.svcdiscovery.api.SpConfig;


import java.io.Serializable;

public class ${classNamePrefix}Program extends FlinkDataProcessorRuntime<${classNamePrefix}Parameters> implements Serializable {

  private static final long serialVersionUID = 1L;
  private final ${classNamePrefix}Parameters params;

  public ${classNamePrefix}Program(${classNamePrefix}Parameters params,
		  ConfigExtractor configExtractor,
		  StreamPipesClient streamPipesClient)
	{
	  super(params, configExtractor, streamPipesClient);
	  this.params = params;
	}


  @Override
    protected FlinkDeploymentConfig getDeploymentConfig(ConfigExtractor configExtractor) {
		SpConfig config = configExtractor.getConfig();
		return new FlinkDeploymentConfig(config.getString(ConfigKeys.FLINK_JAR_FILE_LOC),
				config.getString(ConfigKeys.FLINK_HOST),
				config.getInteger(ConfigKeys.FLINK_PORT),
				config.getBoolean(ConfigKeys.DEBUG));
	}
	@Override
    protected DataStream<Event> getApplicationLogic(DataStream<Event>... dataStreams){

		return dataStreams[0]
				.flatMap(new ${classNamePrefix}(this.params.getExampleText()));
	}
}
