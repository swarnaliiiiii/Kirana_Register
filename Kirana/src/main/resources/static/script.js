const API_BASE_URL = 'http://localhost:8080/api/transactions';  // Change to your API base URL

// Record a Transaction
document.getElementById('recordForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const transaction = {
        userID: document.getElementById('userId').value,
        amount: parseFloat(document.getElementById('amount').value),
        currency: document.getElementById('currency').value,
        type: document.getElementById('type').value,
        date: document.getElementById('date').value
    };

    const response = await fetch(`${API_BASE_URL}/record`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(transaction)
    });

    const result = await response.text();
    alert(result);
});

// Update a Transaction
document.getElementById('updateForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const userId = document.getElementById('updateUserId').value;
    const updatedTransaction = {
        amount: parseFloat(document.getElementById('updateAmount').value),
        currency: document.getElementById('updateCurrency').value,
        type: document.getElementById('updateType').value,
        date: document.getElementById('updateDate').value
    };

    const response = await fetch(`${API_BASE_URL}/update/${userId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedTransaction)
    });

    const result = await response.text();
    alert(result);
});

// Delete a Transaction
document.getElementById('deleteForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const userId = document.getElementById('deleteUserId').value;

    const response = await fetch(`${API_BASE_URL}/delete/${userId}`, {
        method: 'DELETE'
    });

    const result = await response.text();
    alert(result);
});

// Fetch All Transactions
document.getElementById('fetchTransactions').addEventListener('click', async () => {
    const response = await fetch(`${API_BASE_URL}/all`);
    const transactions = await response.json();
    const transactionList = document.getElementById('transactionList');
    transactionList.innerHTML = '';  // Clear the existing list

    transactions.forEach(transaction => {
        const listItem = document.createElement('li');
        listItem.textContent = `UserID: ${transaction.userID}, Amount: ${transaction.amount}, Currency: ${transaction.currency}, Type: ${transaction.type}, Date: ${transaction.date}`;
        transactionList.appendChild(listItem);
    });
});