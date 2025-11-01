import './App.css'
import React, { useState, useEffect, type JSX } from 'react';
import { AlertCircle, CheckCircle, Clock, X, Upload, LogOut, Plus, Settings } from 'lucide-react';

const API_BASE = 'http://localhost:9100/api';

// API Service
const api = {
  async login(email: any, password: any) {
    const res = await fetch(`${API_BASE}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password })
    });
    if (!res.ok) throw new Error('Login failed');
    return res.json();
  },

  async submitExpense(token: any, formData: any) {
    const res = await fetch(`${API_BASE}/expenses`, {
      method: 'POST',
      headers: { 'Authorization': `Bearer ${token}` },
      body: formData
    });
    if (!res.ok) throw new Error('Failed to submit expense');
    return res.json();
  },

  async getMyExpenses(token: any) {
    const res = await fetch(`${API_BASE}/expenses/my-expenses`, {
      headers: { 'Authorization': `Bearer ${token}` }
    });
    if (!res.ok) throw new Error('Failed to fetch expenses');
    return res.json();
  },

  async getPendingExpenses(token: any) {
    const res = await fetch(`${API_BASE}/expenses/pending`, {
      headers: { 'Authorization': `Bearer ${token}` }
    });
    if (!res.ok) throw new Error('Failed to fetch pending expenses');
    return res.json();
  },

  async approveExpense(token: any, id: any, action: any, reason = '') {
    const res = await fetch(`${API_BASE}/expenses/${id}/approve`, {
      method: 'POST',
      headers: { 
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ action, reason })
    });
    if (!res.ok) throw new Error('Failed to process expense');
    return res.json();
  },

  async getWorkflows(token: any) {
    const res = await fetch(`${API_BASE}/workflows`, {
      headers: { 'Authorization': `Bearer ${token}` }
    });
    if (!res.ok) throw new Error('Failed to fetch workflows');
    return res.json();
  },

  async createWorkflow(token: any, jobTitle: any, approverRoles: any[]) {
    const res = await fetch(`${API_BASE}/workflows`, {
      method: 'POST',
      headers: { 
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ jobTitle, approverRoles })
    });
    if (!res.ok) throw new Error('Failed to create workflow');
    return res.json();
  }
};

// Login Component
function Login({ onLogin }: { onLogin: (authData: any) => void }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e: any) => {
    e.preventDefault();
    setError('');
    try {
      const data = await api.login(email, password);
      onLogin(data);
    } catch (err) {
      setError('Invalid credentials');
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center p-4">
      <div className="bg-white rounded-lg shadow-xl p-8 w-full max-w-md">
        <h1 className="text-3xl font-bold text-gray-800 mb-2">Expense Manager</h1>
        <p className="text-gray-600 mb-6">Sign in to manage your expenses</p>
        
        {error && (
          <div className="bg-red-50 text-red-700 p-3 rounded-lg mb-4 flex items-center gap-2">
            <AlertCircle size={20} />
            {error}
          </div>
        )}
        
        <div className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
            />
          </div>
          
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Password</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
            />
          </div>
          
          <button
            onClick={handleSubmit}
            className="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 transition-colors font-medium"
          >
            Sign In
          </button>
        </div>

        <div className="mt-6 pt-6 border-t border-gray-200">
          <p className="text-sm text-gray-600 mb-2">Demo Accounts:</p>
          <div className="space-y-1 text-xs text-gray-500">
            <p>Admin: admin@company.com / admin123</p>
            <p>Engineer: engineer@company.com / engineer123</p>
            <p>Team Lead: teamlead@company.com / teamlead123</p>
            <p>Finance: finance@company.com / finance123</p>
          </div>
        </div>
      </div>
    </div>
  );
}

// Submit Expense Form
function SubmitExpenseForm({ token, onSuccess }: { token: any; onSuccess: () => void }) {
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    amount: '',
    category: 'Travel'
  });
  const [receipt, setReceipt] = useState<File | null>(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async () => {
    if (!formData.title || !formData.amount) return;
    
    setLoading(true);
    const data = new FormData();
    data.append('title', formData.title);
    data.append('description', formData.description);
    data.append('amount', formData.amount);
    data.append('category', formData.category);
    if (receipt) data.append('receipt', receipt);

    try {
      await api.submitExpense(token, data);
      setFormData({ title: '', description: '', amount: '', category: 'Travel' });
      setReceipt(null);
      onSuccess();
    } catch (err) {
      alert('Failed to submit expense');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-white rounded-lg shadow p-6">
      <h2 className="text-xl font-bold text-gray-800 mb-4">Submit New Expense</h2>
      
      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Title</label>
          <input
            type="text"
            value={formData.title}
            onChange={(e) => setFormData({...formData, title: e.target.value})}
            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Description</label>
          <textarea
            value={formData.description}
            onChange={(e) => setFormData({...formData, description: e.target.value})}
            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
            rows={3}
          />
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Amount ($)</label>
            <input
              type="number"
              step="0.01"
              value={formData.amount}
              onChange={(e) => setFormData({...formData, amount: e.target.value})}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Category</label>
            <select
              value={formData.category}
              onChange={(e) => setFormData({...formData, category: e.target.value})}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
            >
              <option>Travel</option>
              <option>Meals</option>
              <option>Office Supplies</option>
              <option>Software</option>
              <option>Other</option>
            </select>
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Receipt (optional)</label>
          <div className="border-2 border-dashed border-gray-300 rounded-lg p-4 text-center">
            <input
              type="file"
              onChange={(e) => setReceipt(e.target.files ? e.target.files[0] : null)}
              className="hidden"
              id="receipt-upload"
              accept="image/*,.pdf"
            />
            <label htmlFor="receipt-upload" className="cursor-pointer flex flex-col items-center gap-2">
              <Upload className="text-gray-400" size={32} />
              <span className="text-sm text-gray-600">
                {receipt ? receipt.name : 'Click to upload receipt'}
              </span>
            </label>
          </div>
        </div>

        <button
          onClick={handleSubmit}
          disabled={loading}
          className="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 transition-colors font-medium disabled:opacity-50"
        >
          {loading ? 'Submitting...' : 'Submit Expense'}
        </button>
      </div>
    </div>
  );
}

// Expense Card
function ExpenseCard({ key, expense, showActions, onApprove, onReject }: { 
  key: any;
  expense: any; 
  showActions: boolean; 
  onApprove: (id: any) => void; 
  onReject: (id: any, reason: any) => void; 
}) {
  const [showRejectModal, setShowRejectModal] = useState(false);
  const [rejectReason, setRejectReason] = useState('');
  console.log('ExpenseCard rendered', key);

  const statusColors: Record<string, string> = {
    PENDING: 'bg-yellow-100 text-yellow-800',
    APPROVED: 'bg-green-100 text-green-800',
    REJECTED: 'bg-red-100 text-red-800'
  };

  const statusIcons: Record<string, JSX.Element> = {
    PENDING: <Clock size={16} />,
    APPROVED: <CheckCircle size={16} />,
    REJECTED: <X size={16} />
  };

  const handleReject = () => {
    if (rejectReason.trim()) {
      onReject?.(expense.id, rejectReason);
      setShowRejectModal(false);
      setRejectReason('');
    }
  };

  return (
    <>
      <div className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition-shadow">
        <div className="flex justify-between items-start mb-4">
          <div>
            <h3 className="text-lg font-bold text-gray-800">{expense.title}</h3>
            <p className="text-sm text-gray-600">{expense.category}</p>
          </div>
          <span className={`px-3 py-1 rounded-full text-sm font-medium flex items-center gap-1 ${statusColors[expense.status]}`}>
            {statusIcons[expense.status]}
            {expense.status}
          </span>
        </div>

        <p className="text-gray-700 mb-3">{expense.description}</p>

        <div className="flex justify-between items-center mb-3">
          <span className="text-2xl font-bold text-blue-600">${expense.amount.toFixed(2)}</span>
          <span className="text-sm text-gray-500">
            {new Date(expense.submittedDate).toLocaleDateString()}
          </span>
        </div>

        <div className="border-t pt-3 space-y-2">
          <div className="flex justify-between text-sm">
            <span className="text-gray-600">Submitted by:</span>
            <span className="font-medium">{expense.submitter.fullName}</span>
          </div>
          
          {expense.currentApprover && (
            <div className="flex justify-between text-sm">
              <span className="text-gray-600">Current Approver:</span>
              <span className="font-medium">{expense.currentApprover.fullName}</span>
            </div>
          )}

          {expense.rejectionReason && (
            <div className="bg-red-50 p-3 rounded-lg mt-2">
              <p className="text-sm text-red-800"><strong>Rejection Reason:</strong> {expense.rejectionReason}</p>
            </div>
          )}
        </div>

        {showActions && expense.status === 'PENDING' && (
          <div className="flex gap-2 mt-4">
            <button
              onClick={() => onApprove(expense?.id)}
              className="flex-1 bg-green-600 text-white py-2 rounded-lg hover:bg-green-700 transition-colors font-medium"
            >
              Approve
            </button>
            <button
              onClick={() => setShowRejectModal(true)}
              className="flex-1 bg-red-600 text-white py-2 rounded-lg hover:bg-red-700 transition-colors font-medium"
            >
              Reject
            </button>
          </div>
        )}
      </div>

      {showRejectModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
          <div className="bg-white rounded-lg p-6 max-w-md w-full">
            <h3 className="text-lg font-bold mb-4">Reject Expense</h3>
            <textarea
              value={rejectReason}
              onChange={(e) => setRejectReason(e.target.value)}
              placeholder="Enter rejection reason..."
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 mb-4"
              rows={4}
            />
            <div className="flex gap-2">
              <button
                onClick={handleReject}
                className="flex-1 bg-red-600 text-white py-2 rounded-lg hover:bg-red-700"
              >
                Reject
              </button>
              <button
                onClick={() => setShowRejectModal(false)}
                className="flex-1 bg-gray-300 text-gray-800 py-2 rounded-lg hover:bg-gray-400"
              >
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}

// Workflow Management
function WorkflowManager({ token }: { token: any }) {
  const [workflows, setWorkflows] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({ jobTitle: '', approverRoles: [''] });

  useEffect(() => {
    loadWorkflows();
  }, []);

  const loadWorkflows = async () => {
    try {
      const data = await api.getWorkflows(token);
      setWorkflows(data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleSubmit = async () => {
    if (!formData.jobTitle || formData.approverRoles.filter(r => r).length === 0) return;
    
    try {
      await api.createWorkflow(token, formData.jobTitle, formData.approverRoles.filter(r => r));
      setFormData({ jobTitle: '', approverRoles: [''] });
      setShowForm(false);
      loadWorkflows();
    } catch (err) {
      alert('Failed to create workflow');
    }
  };

  const addRole = () => {
    setFormData({...formData, approverRoles: [...formData.approverRoles, '']});
  };

  const updateRole = (index: any, value: any) => {
    const roles = [...formData.approverRoles];
    roles[index] = value;
    setFormData({...formData, approverRoles: roles});
  };

  const removeRole = (index: any) => {
    const roles = formData.approverRoles.filter((_, i) => i !== index);
    setFormData({...formData, approverRoles: roles});
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h2 className="text-2xl font-bold text-gray-800">Approval Workflows</h2>
        <button
          onClick={() => setShowForm(!showForm)}
          className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 flex items-center gap-2"
        >
          <Plus size={20} />
          New Workflow
        </button>
      </div>

      {showForm && (
        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-lg font-bold mb-4">Create Workflow</h3>
          <div className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Designation</label>
              <input
                type="text"
                value={formData.jobTitle}
                onChange={(e) => setFormData({...formData, jobTitle: e.target.value})}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Approval Chain</label>
              {formData.approverRoles.map((role, index) => (
                <div key={index} className="flex gap-2 mb-2">
                  <span className="flex items-center justify-center w-8 text-gray-600 font-medium">
                    {index + 1}.
                  </span>
                  <input
                    type="text"
                    value={role}
                    onChange={(e) => updateRole(index, e.target.value)}
                    placeholder="Enter approver role (e.g., Team Lead)"
                    className="flex-1 px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
                  />
                  {formData.approverRoles.length > 1 && (
                    <button
                      onClick={() => removeRole(index)}
                      className="px-3 py-2 bg-red-100 text-red-600 rounded-lg hover:bg-red-200"
                    >
                      <X size={20} />
                    </button>
                  )}
                </div>
              ))}
              <button
                onClick={addRole}
                className="text-blue-600 hover:text-blue-700 text-sm font-medium"
              >
                + Add Another Level
              </button>
            </div>

            <div className="flex gap-2">
              <button
                onClick={handleSubmit}
                className="flex-1 bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700"
              >
                Create Workflow
              </button>
              <button
                onClick={() => setShowForm(false)}
                className="flex-1 bg-gray-300 text-gray-800 py-2 rounded-lg hover:bg-gray-400"
              >
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}

      <div className="grid gap-4">
        {workflows.map((workflow: any) => (
          <div key={workflow.id} className="bg-white rounded-lg shadow p-6">
            <div className="flex justify-between items-start mb-4">
              <h3 className="text-lg font-bold text-gray-800">{workflow.jobTitle}</h3>
              <span className={`px-3 py-1 rounded-full text-sm ${workflow.isActive ? 'bg-green-100 text-green-800' : 'bg-gray-100 text-gray-800'}`}>
                {workflow.isActive ? 'Active' : 'Inactive'}
              </span>
            </div>
            <div className="space-y-2">
              <p className="text-sm font-medium text-gray-700">Approval Chain:</p>
              <div className="flex flex-wrap gap-2">
                {workflow.levels.map((level: any, index: number) => (
                  <React.Fragment key={level.levelOrder}>
                    <span className="bg-blue-50 text-blue-700 px-3 py-1 rounded-lg text-sm font-medium">
                      {index + 1}. {level.approverRole}
                    </span>
                    {index < workflow.levels.length - 1 && (
                      <span className="text-gray-400 flex items-center">→</span>
                    )}
                  </React.Fragment>
                ))}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

// Main Dashboard
function Dashboard({ user, token, onLogout }: { user: any; token: any; onLogout: () => void }) {
  const [activeTab, setActiveTab] = useState('submit');
  const [myExpenses, setMyExpenses] = useState([]);
  const [pendingExpenses, setPendingExpenses] = useState([]);

  useEffect(() => {
    loadData();
  }, [activeTab]);

  const loadData = async () => {
    try {
      if (activeTab === 'myExpenses') {
        const data = await api.getMyExpenses(token);
        setMyExpenses(data);
      } else if (activeTab === 'pending') {
        const data = await api.getPendingExpenses(token);
        setPendingExpenses(data);
      }
    } catch (err) {
      console.error(err);
    }
  };

  const handleApprove = async (id: any) => {
    try {
      await api.approveExpense(token, id, 'APPROVE');
      loadData();
    } catch (err) {
      alert('Failed to approve expense');
    }
  };

  const handleReject = async (id: any, reason: any) => {
    try {
      await api.approveExpense(token, id, 'REJECT', reason);
      loadData();
    } catch (err) {
      alert('Failed to reject expense');
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <nav className="bg-white shadow-sm border-b">
        <div className="max-w-7xl mx-auto px-4 py-4">
          <div className="flex justify-between items-center">
            <div>
              <h1 className="text-2xl font-bold text-gray-800">Expense Manager</h1>
              <p className="text-sm text-gray-600">{user.fullName} • {user.jobTitle} • {user.role}</p>
            </div>
            <button
              onClick={onLogout}
              className="flex items-center gap-2 px-4 py-2 text-gray-700 hover:bg-gray-100 rounded-lg transition-colors"
            >
              <LogOut size={20} />
              Logout
            </button>
          </div>
        </div>
      </nav>

      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="flex gap-2 mb-6 overflow-x-auto">
          <button
            onClick={() => setActiveTab('submit')}
            className={`px-6 py-3 rounded-lg font-medium whitespace-nowrap ${activeTab === 'submit' ? 'bg-blue-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-50'}`}
          >
            Submit Expense
          </button>
          <button
            onClick={() => setActiveTab('myExpenses')}
            className={`px-6 py-3 rounded-lg font-medium whitespace-nowrap ${activeTab === 'myExpenses' ? 'bg-blue-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-50'}`}
          >
            My Expenses
          </button>
          {(user.role === 'MANAGER' || user.role === 'ADMIN') && (
            <button
              onClick={() => setActiveTab('pending')}
              className={`px-6 py-3 rounded-lg font-medium whitespace-nowrap ${activeTab === 'pending' ? 'bg-blue-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-50'}`}
            >
              Pending Approvals
            </button>
          )}
          {user.role === 'ADMIN' && (
            <button
              onClick={() => setActiveTab('workflows')}
              className={`px-6 py-3 rounded-lg font-medium whitespace-nowrap flex items-center gap-2 ${activeTab === 'workflows' ? 'bg-blue-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-50'}`}
            >
              <Settings size={20} />
              Workflows
            </button>
          )}
        </div>

        {activeTab === 'submit' && <SubmitExpenseForm token={token} onSuccess={loadData} />}

        {activeTab === 'myExpenses' && (
          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
            {myExpenses.length === 0 ? (
              <div className="col-span-full text-center py-12 text-gray-500">
                No expenses submitted yet
              </div>
            ) : (
              myExpenses.map((expense: any) => (
                <ExpenseCard key={expense.id} expense={expense} showActions={false} onApprove={handleApprove} onReject={handleReject} />
              ))
            )}
          </div>
        )}

        {activeTab === 'pending' && (
          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
            {pendingExpenses.length === 0 ? (
              <div className="col-span-full text-center py-12 text-gray-500">
                No pending expenses
              </div>
            ) : (
              pendingExpenses.map((expense: any) => (
                <ExpenseCard
                  key={expense.id}
                  expense={expense}
                  showActions={true}
                  onApprove={handleApprove}
                  onReject={handleReject}
                />
              ))
            )}
          </div>
        )}

        {activeTab === 'workflows' && <WorkflowManager token={token} />}
      </div>
    </div>
  );
}

// Main App
function App() {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(null);

  const handleLogin = (authData: any) => {
    setUser(authData);
    setToken(authData.token);
  };

  const handleLogout = () => {
    setUser(null);
    setToken(null);
  };

  if (!user) {
    return <Login onLogin={handleLogin} />;
  }

  return <Dashboard user={user} token={token} onLogout={handleLogout} />;
}

export default App
