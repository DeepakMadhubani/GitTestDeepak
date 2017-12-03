package com.macys.stella.attribute.dbreader.impl;

public class DataRow {
	/**
	 * Initializes a new {@link DataRow}, referencing an owner {@link DataReader} and a collection of records sorted by the
	 * owner's column order
	 * 
	 * @param owner
	 *            The {@link DataReader} containing this row's schema data
	 * @param records
	 *            The collection of records that make up this row of data
	 */
	public DataRow(DataReader owner, Object[] records) {
		this.owner = owner;
		this.records = records;
	}

	/**
	 * The {@link DataReader} owner of this {@link DataRow}, containing schema information used to safely navigate columns
	 */
	private DataReader owner;
	/**
	 * The collection of records in this {@link DataRow}
	 */
	private Object[] records;

	/**
	 * Retrieves a record in this {@link DataRow}, cast to type T
	 * 
	 * @param type
	 *            the class definition of the casting type
	 * @param columnIndex
	 *            the first column is 1, the second is 2, ...
	 * @return the column value; if the value is SQL {@code NULL}, the value returned is {@code null}
	 * @throws IndexOutOfBoundsException
	 *             if the columnIndex does not fall within the bounds of the {@link DataRow}
	 * @throws ClassCastException
	 *             if the record at this columnIndex is not of type T
	 */
	public <T> T get(Class<T> type, int columnIndex) throws IndexOutOfBoundsException, ClassCastException {
		if(columnIndex <= 0) {
			throw new IndexOutOfBoundsException(String.format("Position %s is below the minimum range of DataRow values.", columnIndex));
		}
		if(columnIndex > owner.getColumnCount()) {
			throw new IndexOutOfBoundsException(String.format("DataRow has no record at position %s.", columnIndex));
		}
		if(records[columnIndex - 1] == null) {
			return null;
		}
		try {
			return type.cast(records[columnIndex - 1]);
		}
		catch(ClassCastException ex) {
			throw new ClassCastException(String.format("DataRow record at position %s cannot be cast from type %s to %s.", columnIndex,
					records[columnIndex].getClass().getSimpleName(), type.getSimpleName()));
		}
	}

	/**
	 * Retrieves a record in this {@link DataRow}, cast to type T
	 * 
	 * @param type
	 *            the class definition of the casting type
	 * @param columnLabel
	 *            the label for the column specified with the SQL AS clause. If the SQL AS clause was not specified, then the
	 *            label is the name of the column
	 * @return the column value; if the value is SQL {@code NULL}, the value returned is {@code null}
	 * @throws IndexOutOfBoundsException
	 *             if the columnLabel is not present in the {@link DataReader}
	 * @throws ClassCastException
	 *             if the record at this columnLabel is not of type T
	 */
	public <T> T get(Class<T> type, String columnLabel) throws IndexOutOfBoundsException, ClassCastException {
		int columnIndex = owner.findColumn(columnLabel);
		if(columnIndex == -1) {
			throw new IndexOutOfBoundsException(String.format("Column '%s' was not found in DataReader.", columnLabel));
		}
		return get(type, columnIndex);
	}

	/**
	 * Retrieves the type of a record in this {@link DataRow}
	 * 
	 * @param columnIndex
	 *            the first column is 1, the second is 2, ...
	 * @return the class type of the record at this columnIndex; if the value is SQL {@code NULL}, the value returned is
	 *         {@code null}
	 */
	public Class<?> getType(int columnIndex) {
		if(columnIndex <= 0) {
			throw new IndexOutOfBoundsException(String.format("Position %s is below the minimum range of DataRow values.", columnIndex));
		}
		if(columnIndex > owner.getColumnCount()) {
			throw new IndexOutOfBoundsException(String.format("DataRow has no record at position %s.", columnIndex));
		}
		if(records[columnIndex - 1] == null) {
			return null;
		}
		return records[columnIndex - 1].getClass();
	}

	/**
	 * Retrieves the type of a record in this {@link DataRow}
	 * 
	 * @param columnLabel
	 *            the label for the column specified with the SQL AS clause. If the SQL AS clause was not specified, then the
	 *            label is the name of the column
	 * @return the class type of the record at this columnLabel; if the value is SQL {@code NULL}, the value returned is
	 *         {@code null}
	 */
	public Class<?> getType(String columnLabel) {
		int columnIndex = owner.findColumn(columnLabel) + 1;
		if(columnIndex <= 0) {
			throw new IndexOutOfBoundsException(String.format("Column '%s' was not found in Data Reader.", columnLabel));
		}
		return getType(columnIndex);
	}

	/**
	 * Retrieves the {@link DataReader} owner of this {@link DataRow}
	 * 
	 * @return the {@link DataReader}
	 */
	public DataReader getOwner() {
		return owner;
	}
}

