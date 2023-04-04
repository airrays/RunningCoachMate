from datetime import datetime
import time


def localtime2str(localtime):
    """
    Convert local datetime to a string, e.g. '2022-09-26'.

    :param localtime: Local datetime
    :type localtime: datetime
    :return: A string representing the local date
    :rtype: str
    """

    return localtime.strftime('%Y-%m-%d')


def localtime2unix(localtime):
    """
    Convert local datetime to unix timestamp.

    :param localtime: A local datetime
    :type localtime: datetime
    :return: The unix timestamp
    :rtype: int
    """

    return int(time.mktime(localtime.timetuple()))


def unix2localtime(unix):
    """
    Convert unix timestamp to local datetime.

    :param unix: A unix timestamp
    :type unix: int
    :return: The local datetime
    :rtype: datetime
    """

    return datetime.fromtimestamp(unix)


def unix2str(unix):
    """
    Convert a unix timestamp to local date in a string format, e.g. '2022-09-26'.

    :param unix: A unix timestamp
    :type unix: int
    :return: A string representing the local date
    :rtype: str
    """

    localtime = unix2localtime(unix)
    return localtime2str(localtime)


def str2unix(date_str):
    """
    Convert a string representing a local date (e.g. '2022-09-26') to unix timestamp.

    :param date_str: Local date string in format '%Y-%m-%d'
    :type date_str: str
    :return: The unix timestamp
    :rtype: int
    """

    localtime = datetime.strptime(date_str, '%Y-%m-%d')
    return localtime2unix(localtime)


def unix_now():
    """
    Get unix timestamp of current local time.

    :return: A unix timestamp representing current local datetime
    :rtype: int
    """

    localtime = datetime.now()
    return localtime2unix(localtime)


def unix_30d_before(unix):
    """
    Get the unix timestamp 30 days before the given unix time.

    :param unix: A unix timestamp
    :type unix: int
    :return: The unix timestamp 30 days before
    :rtype: int
    """

    return unix - (30 * 24 * 3600)


def unix_days_before(unix, days):
    """
    Get the unix timestamp of the specified date before the given unix time.

    :param unix: A unix timestamp
    :type unix: int
    :param days: Specifies how many days before the given unix time
    :type days: int
    :return: The unix timestamp of the specified date before given unix time
    :rtype: int
    """

    return unix - (days * 24 * 3600)

