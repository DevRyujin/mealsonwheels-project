import React, { useEffect, useState } from "react";
import axiosInstance from "../../api/axiosInstance";

const CaregiversAndMembers = () => {
  const [caregivers, setCaregivers] = useState([]);
  const [members, setMembers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [caregiverSearch, setCaregiverSearch] = useState("");
  const [memberSearch, setMemberSearch] = useState("");

  useEffect(() => {
    const fetchCaregiversAndMembers = async () => {
      try {
        const [caregiversRes, membersRes] = await Promise.all([
          axiosInstance.get("/admin/caregivers-with-member-info"),
          axiosInstance.get("/admin/members-without-caregivers"),
        ]);
        setCaregivers(caregiversRes.data);
        setMembers(membersRes.data);
      } catch (err) {
        console.error("Failed to fetch caregivers or members:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchCaregiversAndMembers();
  }, []);

  const filteredCaregivers = caregivers.filter((cg) =>
    `${cg.caregiverName} ${cg.memberNameToAssist}`
      .toLowerCase()
      .includes(caregiverSearch.toLowerCase())
  );

  const filteredMembers = members.filter((member) =>
    member.name.toLowerCase().includes(memberSearch.toLowerCase())
  );

  return (
    <div className="p-6 space-y-10">
      <h1 className="text-3xl font-bold mb-2 text-center">Members and Caregivers</h1>
      <p className="text-gray-700 mb-6 font-medium text-center">
        Overview of approved caregivers and members information.
      </p>

      {/* === Caregivers with Members === */}
      <div className="flex justify-between items-center mb-2">
        <h2 className="text-2xl font-bold">Members with Caregivers</h2>
        <input
          type="text"
          placeholder="Search caregivers or members..."
          value={caregiverSearch}
          onChange={(e) => setCaregiverSearch(e.target.value)}
          className="border rounded px-2 py-1 text-sm w-64"
        />
      </div>

      <div className="overflow-x-auto mb-10 rounded-xl">
        <table className="w-full table-fixed">
          <thead className="bg-gray-100 text-sm text-center">
            <tr>
              <th className="p-2 w-[180px]">Caregiver Name</th>
              <th className="p-2 w-[200px]">Caregiver Email</th>
              <th className="p-2 w-[140px]">Caregiver Phone</th>
              <th className="p-2 w-[180px]">Member Name</th>
              <th className="p-2 w-[140px]">Member Phone</th>
              <th className="p-2 w-[200px]">Member Address</th>
              <th className="p-2 w-[120px]">Relationship</th>
              <th className="p-2 w-[200px]">Qualifications</th>
              <th className="p-2 w-[100px]">Status</th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr>
                <td colSpan={9} className="text-center py-4 text-gray-500">
                  Loading caregivers...
                </td>
              </tr>
            ) : filteredCaregivers.length === 0 ? (
              <tr>
                <td colSpan={9} className="text-center py-4 text-gray-500">
                  No caregivers found.
                </td>
              </tr>
            ) : (
              filteredCaregivers.map((cg, index) => (
                <tr
                  key={index}
                  className="text-sm text-center hover:bg-gray-50 transition duration-200"
                >
                  <td className="p-2">{cg.caregiverName}</td>
                  <td className="p-2">{cg.caregiverEmail}</td>
                  <td className="p-2">{cg.caregiverPhone}</td>
                  <td className="p-2">{cg.memberNameToAssist}</td>
                  <td className="p-2">{cg.memberPhoneNumberToAssist}</td>
                  <td className="p-2">{cg.memberAddressToAssist}</td>
                  <td className="p-2">{cg.memberRelationship}</td>
                  <td className="p-2">{cg.qualificationsAndSkills}</td>
                  <td className="p-2">
                    {cg.approved ? (
                      <span className="text-green-600 font-medium">Approved</span>
                    ) : (
                      <span className="text-yellow-600 font-medium">Pending</span>
                    )}
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      {/* === Members Without Caregivers === */}
      <div className="flex justify-between items-center mb-2">
        <h2 className="text-2xl font-bold">Registered Members Without Caregivers</h2>
        <input
          type="text"
          placeholder="Search members..."
          value={memberSearch}
          onChange={(e) => setMemberSearch(e.target.value)}
          className="border rounded px-2 py-1 text-sm w-64"
        />
      </div>

      <div className="overflow-x-auto rounded-xl">
        <table className="w-full table-fixed">
          <thead className="bg-gray-100 text-sm text-center">
            <tr>
              <th className="p-2 w-[180px]">Member Name</th>
              <th className="p-2 w-[200px]">Email</th>
              <th className="p-2 w-[140px]">Phone</th>
              <th className="p-2 w-[200px]">Address</th>
              <th className="p-2 w-[200px]">Dietary Restrictions</th>
              <th className="p-2 w-[100px]">Status</th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr>
                <td colSpan={6} className="text-center py-4 text-gray-500">
                  Loading members...
                </td>
              </tr>
            ) : filteredMembers.length === 0 ? (
              <tr>
                <td colSpan={6} className="text-center py-4 text-gray-500">
                  No members found.
                </td>
              </tr>
            ) : (
              filteredMembers.map((member, index) => (
                <tr
                  key={index}
                  className="text-sm text-center hover:bg-gray-50 transition duration-200"
                >
                  <td className="p-2">{member.name}</td>
                  <td className="p-2">{member.email}</td>
                  <td className="p-2">{member.phone}</td>
                  <td className="p-2">{member.address}</td>
                  <td className="p-2">
                    {member.dietaryRestrictions?.join(", ") || "None"}
                  </td>
                  <td className="p-2">
                    {member.approved ? (
                      <span className="text-green-600 font-medium">Approved</span>
                    ) : (
                      <span className="text-red-600 font-medium">Pending</span>
                    )}
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default CaregiversAndMembers;
