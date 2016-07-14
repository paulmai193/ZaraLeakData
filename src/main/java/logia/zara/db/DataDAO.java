package logia.zara.db;

import java.util.Map;

import logia.redis.dao.HashRedisDAO;
import logia.redis.util.Redis;

/**
 * The Class DataDAO.
 *
 * @author Paul Mai
 */
public class DataDAO extends HashRedisDAO<Data> {

	/** The Constant instance. */
	private static final DataDAO instance = new DataDAO();

	/**
	 * Gets the single instance of DataDAO.
	 *
	 * @return single instance of DataDAO
	 */
	public static DataDAO getInstance() {
		return DataDAO.instance;
	}

	/**
	 * Instantiates a new data DAO.
	 */
	private DataDAO() {
		super();
	}

	/* (non-Javadoc)
	 * @see logia.redis.dao.HashRedisDAO#get(java.lang.String)
	 */
	@Override
	public Data get(String __ref) {
		Redis _redis = new Redis();
		String _key = Data.KEY.replace("<ref>", __ref);
		Data _data;
		Map<String, String> _value = null;
		try {
			_value = _redis.getJedis().hgetAll(_key);
			_data = new Data();
			_data.setKey(_key);
			_data.setValue(_value);
		}
		catch (Exception _e) {
			this.LOGGER.error("Error when get Data Object from DB with key " + _key, _e);
			_data = null;
		}
		finally {
			_redis.quitJedis();
		}
		return _data;
	}

	/* (non-Javadoc)
	 * @see logia.redis.dao.AbstractRedisDAO#getPrefixKey()
	 */
	@Override
	public String getPrefixKey() {
		return Data.KEY.replace("<ref>", "*");
	}

}
