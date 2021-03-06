package com.linkedin.chirper.search

import com.linkedin.led.twitter.config._

import com.linkedin.chirper.DefaultConfigs

import org.json.JSONObject

import proj.zoie.api.DefaultZoieVersion
import proj.zoie.api.DefaultZoieVersion.DefaultZoieVersionFactory
import proj.zoie.hourglass.impl.HourGlassScheduler
import proj.zoie.hourglass.impl.HourGlassScheduler.FREQUENCY

import proj.zoie.impl.indexing.ZoieConfig

import java.util._
import java.text.SimpleDateFormat

import com.linkedin.norbert.javacompat.cluster.{ClusterClient,ZooKeeperClusterClient}
import com.linkedin.norbert.javacompat.network.{NetworkServer,NettyNetworkServer}
import com.sensei.search.nodes.{SenseiHourglassFactory,SenseiIndexLoaderFactory,SenseiIndexReaderDecorator,SenseiServer}
import com.sensei.search.nodes.impl._


import java.io.File

// Build a search node
object ChirpSearchNode{
	def main(args: Array[String]) = {
	
	  val nodeid = Config.readInt("tweet.search.node.id")
	  val port = Config.readInt("tweet.search.node.port")
	  val partList = Config.readString("tweet.search.node.partitions")
	
	  // where to put the index
	  val idxDir = new File(Config.readString("tweet.search.node.index.dir"))
	
	  // rolls daily at midnight, keep 7 days
	  val hfFactory = new SenseiHourglassFactory[JSONObject, DefaultZoieVersion](idxDir,ChirpSearchConfig.interpreter,
                          new SenseiIndexReaderDecorator(ChirpSearchConfig.handlerList,null), 
	                      DefaultConfigs.zoieConfig, "00 00 00", 7, FREQUENCY.DAILY)
	
	  val clusterName = Config.readString("tweet.zookeeper.cluster")

      // zookeeper cluster client
      val clusterClient = new ZooKeeperClusterClient(clusterName,DefaultConfigs.zkurl,DefaultConfigs.timeout);

      // build a default netty-based network server
      val networkServer = SenseiBuilderHelper.buildDefaultNetworkServer(clusterClient);
		
      // builds the server
	  val server = new SenseiServer(nodeid, port, partList.split(",").map{i=>i.toInt},
			                      idxDir,networkServer,
			                      clusterClient,hfFactory,ChirpSearchConfig.tweetIndexLoaderFactory,DefaultConfigs.queryBuilderFactory)
			
	  DefaultConfigs.addShutdownHook{ server.shutdown }
	
	  // starts the server
	  server.start(true)
		
    }	
}

