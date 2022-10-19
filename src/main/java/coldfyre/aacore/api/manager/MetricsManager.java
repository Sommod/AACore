package coldfyre.aacore.api.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import coldfyre.aacore.api.Metrics;;

/**
 * A set of functions to add different charts
 * of Metrics to the bStats website.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 *
 */
public class MetricsManager {

	private Metrics metrics;
	private int id;
	private Plugin plugin;
	
	/**
	 * Constructor of the bStats Metric Handler.<br>
	 * <a href="https://bstats.org/what-is-my-plugin-id">What is my plugin id?</a>
	 * @param plugin - Plugin to attach Metrics to.
	 * @param id - Id of plugin. (You can find this on bStats).
	 */
	public MetricsManager(JavaPlugin plugin, int id) {
		this.plugin = plugin;
		this.id = id;
		metrics = new Metrics(plugin, id);
	}
	
	/**
	 * Gets the Id of the metrics.
	 * @return ID
	 */
	public int getMetricId() { return id; }
	
	/**
	 * Gets the Plugin associated with the Metrics.
	 * @return plugin.
	 */
	public Plugin getPlugin() { return plugin; }
	
	/**
	 * Gets the Raw object of the Metrics class from bStats.
	 * @return Metrics
	 */
	public Metrics getRawMetrics() { return metrics; }
	
	/**
	 * Adds a simple Pie chart to the bStats website
	 * based on the chartId.
	 * @param chartId - Id (name) of chart.
	 * @param value - Value to pass to chart.
	 */
	public void addSimplePieChart(String chartId, String value) {
		addSimplePieChart(chartId, asCallable(value));
	}
	
	/**
	 * Adds a simple Pie chart to the bStats website
	 * based on the chartId.
	 * @param chartId - Id (Name) of chart.
	 * @param toCall - Callable object of String.
	 */
	public void addSimplePieChart(String chartId, Callable<String> toCall) {
		metrics.addCustomChart(new Metrics.SimplePie(chartId, toCall));
	}
	
	/**
	 * Adds an Advanced Pie Chart to the bStats website.
	 * @param chartId - Id (Name) of chart.
	 * @param valueNames - Array of String values
	 * @param values - Array of Integers that coincide with valueNames
	 */
	public void addAdvancedPieChart(String chartId, String[] valueNames, int[] values) {
		Map<String, Integer> temp = new HashMap<>();
		for(int i = 0; i < valueNames.length; i++)
			temp.put(valueNames[i], values[i]);
		
		addAdvancedPieChart(chartId, asCallable(temp));
	}
	
	/**
	 * Adds an Advnaced Pie chart to the bStats website.
	 * @param chartId - Id (Name) of Chart.
	 * @param values - Map of values.
	 */
	public void addAdvancedPieChart(String chartId, Map<String, Integer> values) {
		addAdvancedPieChart(chartId, asCallable(values));
	}
	
	/**
	 * Adds an Advanced Pie Chart to the bStats website.
	 * @param chartId - Id (Name) of chart.
	 * @param collectable - Callable object of Map{@code <String, Integer>}
	 */
	public void addAdvancedPieChart(String chartId, Callable<Map<String, Integer>> collectable) {
		metrics.addCustomChart(new Metrics.AdvancedPie(chartId, collectable));
	}
	
	/**
	 * Adds a Drilldown Pie Chart to bStats
	 * @param chartId - Id (Name) of chart.
	 * @param values - Map of values  to add.
	 */
	public void addDrilldownPieChart(String chartId, Map<String, Map<String, Integer>> values) {
		addDrilldownPieChart(chartId, asCallable(values));
	}
	
	/**
	 * Adds a Drilldown Pie Chart to bStats
	 * @param chartId - Id (Name) of Chart.
	 * @param collectable - Callable object of Map{@code<String, Map<String, Integer>>}
	 */
	public void addDrilldownPieChart(String chartId, Callable<Map<String, Map<String, Integer>>> collectable) {
		metrics.addCustomChart(new Metrics.DrilldownPie(chartId, collectable));
	}
	
	/**
	 * Adds a Single Line Chart to bStats
	 * @param chartId - Id (Name) of Chart.
	 * @param value - Value to add to chart.
	 */
	public void addSingleLineChart(String chartId, int value) {
		addSingleLineChart(chartId, asCallable(value));
	}
	
	/**
	 * Adds a Single Line Chart to bStats
	 * @param chartId - Id (Name) of Chart.
	 * @param collectable - Callable object of Integer
	 */
	public void addSingleLineChart(String chartId, Callable<Integer> collectable) {
		metrics.addCustomChart(new Metrics.SingleLineChart(chartId, collectable));
	}
	
	/**
	 * Adds a Multi-Line Chart to bStats
	 * @param chartId - Id (Name) of Chart
	 * @param valueNames - Array of String names
	 * @param values - Array of Integer values coincide with valueNames
	 */
	public void addMultiLineChart(String chartId, String[] valueNames, int[] values) {
		Map<String, Integer> temp = new HashMap<>();
		for(int i = 0; i < valueNames.length; i++)
			temp.put(valueNames[i], values[i]);
		addMultiLineChart(chartId, asCallable(temp));
		
	}
	
	/**
	 * Adds a Multi-Line Chart to bStats
	 * @param chartId - Id (Name) of chart.
	 * @param values - Map of Values to add.
	 */
	public void addMultiLineChart(String chartId, Map<String, Integer> values) {
		addMultiLineChart(chartId, asCallable(values));
	}
	
	/**
	 * Adds a Multi-Line Chart to bStats.
	 * @param chartId - Id (Name) of chart.
	 * @param collectable - Callable object of Map{@code <String, Integer>}
	 */
	public void addMultiLineChart(String chartId, Callable<Map<String, Integer>> collectable) {
		metrics.addCustomChart(new Metrics.MultiLineChart(chartId, collectable));
	}
	
	/**
	 * Adds a Simple Bar Chat to bStats
	 * @param chartId - Id (Name) of Chart.
	 * @param valueNames - Array of String names
	 * @param values - Array of Integers that coincide with valueNames
	 */
	public void addSimpleBarChart(String chartId, String[] valueNames, int[] values) {
		Map<String, Integer> temp = new HashMap<>();
		for(int i = 0; i < valueNames.length; i++)
			temp.put(valueNames[i], values[i]);
		addSimpleBarChart(chartId, asCallable(temp));
	}
	
	/**
	 * Adds a Simple Bar Chart to bStats
	 * @param chartId - Id (Name) of Chart.
	 * @param values - Values to add to chart.
	 */
	public void addSimpleBarChart(String chartId, Map<String, Integer> values) {
		addSimpleBarChart(chartId, asCallable(values));
	}
	
	/**
	 * Adds a Simple Bar Chart to bStats
	 * @param chartId - Id (Name) of Chart.
	 * @param collectable - Callable object of Map{@code <String, Integer>}
	 */
	public void addSimpleBarChart(String chartId, Callable<Map<String, Integer>> collectable) {
		metrics.addCustomChart(new Metrics.SimpleBarChart(chartId, collectable));
	}
	
	/**
	 * Adds an Advanced Bar Chart o bStats
	 * @param chartId - Id (Name) of Chart.
	 * @param values - Map of Values to add.
	 */
	public void addAdvancedBarChart(String chartId, Map<String, int[]> values) {
		addAdvancedBarChart(chartId, asCallable(values));
	}
	
	/**
	 * Adds an Advanced Bar Chart to bStats.
	 * @param chartId - Id (Name) of Chart
	 * @param collectable - Callable Object of Map{@code <String, int[]>}
	 */
	public void addAdvancedBarChart(String chartId, Callable<Map<String, int[]>> collectable) {
		metrics.addCustomChart(new Metrics.AdvancedBarChart(chartId, collectable));
	}
	
	/**
	 * Converts the given object into a callable object.
	 * @param toConvert - Object to change into a callable.
	 * @param <V> - Type of object to convert to.
	 * @return Callable{@code <toConvert>}
	 */
	public <V> Callable<V> asCallable(V toConvert) {
		return new Callable<V>() {

			@Override
			public V call() throws Exception {
				return toConvert;
			}
			
		};
	}
}
